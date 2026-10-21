package com.ansub.tvhome;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.media.session.PlaybackState;
import android.os.Handler;
import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Reads the active music/video session without depending on a specific player app. */
final class NowPlaying {
    private static final long PAUSED_FOR_MS = 3 * 60_000L;

    static final class Track {
        final String title, artist;
        final boolean playing;
        final MediaController controller;

        Track(String title, String artist, boolean playing, MediaController controller) {
            this.title = title;
            this.artist = artist;
            this.playing = playing;
            this.controller = controller;
        }
    }

    interface Listener { void onNowPlaying(Track track); }

    private final Context context;
    private final Handler handler;
    private final Listener listener;
    private final ComponentName component;
    private MediaSessionManager manager;
    private final List<MediaController> controllers = new ArrayList<>();
    private final Set<android.media.session.MediaSession.Token> seenPlaying = new HashSet<>();
    private final Map<android.media.session.MediaSession.Token, Long> pausedSince = new HashMap<>();
    private final MediaController.Callback callback = new MediaController.Callback() {
        @Override public void onPlaybackStateChanged(PlaybackState state) { publish(); }
        @Override public void onMetadataChanged(MediaMetadata metadata) { publish(); }
        @Override public void onSessionDestroyed() { publish(); }
    };
    private final MediaSessionManager.OnActiveSessionsChangedListener sessions = this::track;
    private final Runnable recheck = this::publish;

    NowPlaying(Context context, Handler handler, Listener listener) {
        this.context = context;
        this.handler = handler;
        this.listener = listener;
        this.component = new ComponentName(context, MediaListener.class);
    }

    static boolean hasAccess(Context context) {
        String enabled = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        return enabled != null && enabled.contains(componentName(context).flattenToString());
    }

    private static ComponentName componentName(Context context) {
        return new ComponentName(context, MediaListener.class);
    }

    void start() {
        if (manager != null || !hasAccess(context)) return;
        try {
            manager = context.getSystemService(MediaSessionManager.class);
            manager.addOnActiveSessionsChangedListener(sessions, component, handler);
            track(manager.getActiveSessions(component));
        } catch (SecurityException ignored) {
            manager = null;
        }
    }

    void stop() {
        handler.removeCallbacks(recheck);
        if (manager != null) manager.removeOnActiveSessionsChangedListener(sessions);
        manager = null;
        for (MediaController c : controllers) c.unregisterCallback(callback);
        controllers.clear();
    }

    private void track(List<MediaController> list) {
        for (MediaController c : controllers) c.unregisterCallback(callback);
        controllers.clear();
        Set<android.media.session.MediaSession.Token> live = new HashSet<>();
        if (list != null) for (MediaController c : list) {
            c.registerCallback(callback, handler);
            controllers.add(c);
            live.add(c.getSessionToken());
        }
        seenPlaying.retainAll(live);
        pausedSince.keySet().retainAll(live);
        publish();
    }

    private void publish() {
        handler.removeCallbacks(recheck);
        MediaController best = null;
        boolean bestPlaying = false;
        long pausedLeft = -1;
        long now = android.os.SystemClock.elapsedRealtime();
        for (MediaController c : controllers) {
            PlaybackState state = c.getPlaybackState();
            MediaMetadata metadata = c.getMetadata();
            if (state == null || metadata == null) continue;
            android.media.session.MediaSession.Token token = c.getSessionToken();
            int value = state.getState();
            boolean playing = value == PlaybackState.STATE_PLAYING || value == PlaybackState.STATE_BUFFERING;
            boolean paused = value == PlaybackState.STATE_PAUSED && seenPlaying.contains(token);
            if (playing) seenPlaying.add(token);
            if (value != PlaybackState.STATE_PAUSED) pausedSince.remove(token);
            if (paused) {
                Long since = pausedSince.get(token);
                if (since == null) {
                    since = Math.min(now, state.getLastPositionUpdateTime());
                    pausedSince.put(token, since);
                }
                long left = PAUSED_FOR_MS - (now - since);
                if (left <= 0) paused = false;
                else if (best == null) pausedLeft = left;
            }
            if (playing && !bestPlaying) {
                best = c;
                bestPlaying = true;
            } else if (paused && best == null) best = c;
        }
        if (best == null) {
            listener.onNowPlaying(null);
            return;
        }
        if (!bestPlaying && pausedLeft > 0) handler.postDelayed(recheck, pausedLeft + 500);
        MediaMetadata metadata = best.getMetadata();
        String title = first(metadata, MediaMetadata.METADATA_KEY_DISPLAY_TITLE, MediaMetadata.METADATA_KEY_TITLE);
        if (title.isEmpty()) {
            listener.onNowPlaying(null);
            return;
        }
        String artist = first(metadata, MediaMetadata.METADATA_KEY_DISPLAY_SUBTITLE,
                MediaMetadata.METADATA_KEY_ARTIST, MediaMetadata.METADATA_KEY_ALBUM_ARTIST);
        listener.onNowPlaying(new Track(title, artist, bestPlaying, best));
    }

    private static String first(MediaMetadata metadata, String... keys) {
        for (String key : keys) {
            CharSequence value = metadata.getText(key);
            if (value != null && !value.toString().trim().isEmpty()) return value.toString().trim();
        }
        return "";
    }

    static void open(Context context, Track track) {
        PendingIntent session = track.controller.getSessionActivity();
        try {
            if (session != null) {
                session.send();
                return;
            }
        } catch (PendingIntent.CanceledException ignored) {}
        Intent launch = context.getPackageManager().getLeanbackLaunchIntentForPackage(track.controller.getPackageName());
        if (launch == null) launch = context.getPackageManager().getLaunchIntentForPackage(track.controller.getPackageName());
        if (launch != null) context.startActivity(launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
