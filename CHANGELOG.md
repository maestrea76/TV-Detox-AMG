# Changelog

Notable changes to TV Detox AMG. The project is independent as of 22 October
2026: versions up to 1.5.1 come from the TV Detox codebase it started from, and
from now on this repository sets its own version numbers.

## 1.6.2 — 24 October 2026

- The Weather menu offers **Default city** once a city has been picked in Choose location, which
  forgets that pick and goes back to the built-in city instead of leaving the home screen stuck with
  the one it replaced (`MainActivity`).

## 1.6.1 — 24 October 2026

- The inputs pill is named **Inputs** again and its menu lists every HDMI input, as the tiles it
  replaced did. There is no current input on a home screen, so the pill no longer claims one: in
  1.6.0 it named the first input (or the last one picked) and could say `Fire TV` while the TV was
  showing another HDMI (`MainActivity`).
- The Settings pill now sits immediately left of the network icon, with the inputs pill left of
  Settings, the same order the old tiles had (`MainActivity`).
- With the utility row gone, the apps move down into the space it left instead of staying one row
  higher than they need to (`MainActivity`).
- The focus shadow is larger and brighter, and the focused card's rim is a full white, so the
  selection reads from across the room (`MainActivity`).

## 1.6.0 — 24 October 2026

- The utility row under the apps is gone. Settings is now a pill on the clock's line, left of the
  network icon, and the TV's HDMI inputs are a second pill beside it. The input pill takes the name
  of the entry picked in its menu (`HDMI 1`, `Bedroom`…), so both the pick and the way in are one
  line of text instead of a row of tiles. That frees a whole row of space for apps (`MainActivity`).
- The Settings pill's menu now includes **Add app** and **Google Play** alongside Wallpaper, Weather
  and Language, so installing more apps no longer needs its own tiles.
- The focused tile, icon or pill gets a soft blurred shadow of its own shape, so the selection is
  easy to pick out against the wallpaper (`MainActivity`).
- `install.sh` now downloads the fallback APK from this repository's latest release instead of the
  upstream TV Detox one, so installing without a local build no longer pulls the original project's
  app.

## 1.5.2 — 22 October 2026

- Extra large weather: with two rows of apps on the home screen, the seven-day strip
  shows each day on one line (day, sky, high/low) instead of three, and the app grid
  now starts below the header's measured height. Together they stop the tiles from
  covering the forecast temperatures on 1080p TVs (`MainActivity`).

## 1.5.1 — independent line (versionName 1.5.1, versionCode 14)

Home screen, all in `src/com/ansub/tvhome/`:

- Glass-style surfaces and focus states on app, system and HDMI tiles
  (`MainActivity`, `89af3a5`).
- New header with a large clock, localized date, greeting and timeline, plus a
  weather line from Open-Meteo refreshed every 30 minutes (`MainActivity`).
- Weather has four display modes — Small (icon and temperature), Compact (adds
  the city), Large (conditions, feels-like, humidity, wind) and Extra large
  (adds today's high/low and a seven-day strip with each day's sky, high and
  low) — and a city search that saves the choice. The form stays at the top so
  the TV keyboard does not cover it. Default location: Getaria, Gipuzkoa
  (`f8593d9`, `3d9faf5`).
- Now-playing pill backed by a notification listener: shows the active media and
  returns to the player when selected. Adds `NowPlaying.java`,
  `MediaListener.java`, the notification listener service and its manifest
  entries; `install.sh` grants notification access when it can (`37f3ae4`).
- Settings menu now offers Wallpaper, Weather, Language and TV settings.
- Language setting switches the home screen labels between Spanish and English;
  with nothing saved, labels follow the TV (`a762034`, `Strings.java`).
- Spanish README and updated English/Spanish documentation (`ea8fbb7`,
  `aeb4082`).

## 1.5.1 — 2026-10-20 (versionCode 14)

- OLED TVs keep their screensaver: the setup prompt no longer turns it off on
  every TV, and both Android and Google TV screensavers are marked **keep**.
- Missing translations fall back to English instead of crashing the home screen.

## 1.5.0 — 2026-10-20 (versionCode 13)

- 14 gradient wallpapers, 7 dark and 7 bright, chosen from the Settings tile's
  menu. Moving through the list previews each one; OK keeps it, Back restores
  the previous one. Colours only: no images, permissions or extra memory.
- Settings tile opens a small menu (Wallpaper, TV settings).
- Fix: an app opens only when OK goes down on its tile, so Netflix's "Exit" no
  longer reopens Netflix.
- Fix: a lost connection during install is reported as such instead of blaming
  the home screen.
- Fix: `check.sh` accepts Google TV's home screen when `install.sh` left it on.
- Fix: `check.sh` and `undo.sh` handle the install list under Git Bash on
  Windows.

## 1.4.2 — 2026-10-20 (versionCode 12)

- Philips TVs: HDMI tiles show the input name without the `&DEVICE_TYPE` suffix.

## 1.4.1 — 2026-10-20 (versionCode 11)

- Safety change for Google TV and Chromecast: `install.sh` leaves Google TV's own
  home screen on unless `GOOGLE_TV=1` is set. Cleanup still works; only the
  home screen replacement is skipped.

## 1.4.0 — 2026-10-20 (versionCode 10)

- New `candidates.sh`: classifies installed apps as never / keep / safe / review
  without changing anything, plus `docs/debloat.md` with the rules.
- Google TV setup safety net: if the stock home screen cannot be replaced on
  Google TV, `install.sh` turns it back on instead of leaving the TV without one.
- Uninstalling an app from the menu removes its tile immediately and keeps the
  focus on the next app.
- `check.sh` stops and explains when it cannot read the app list.
- CI lints every shell script with ShellCheck; releases wait for it.
- TCL Google TV notes in the README.

## 1.3.0 — 2026-10-19 (versionCode 9)

- Home screen labels and the date follow the TV's language: 16 languages
  (Arabic, Chinese Simplified and Traditional, Dutch, English, French, German,
  Hindi, Indonesian, Italian, Japanese, Korean, Polish, Portuguese (Brazil),
  Russian, Spanish and Turkish).
- Fix: on some Google TVs (for example Sony BRAVIA) the installer could open
  Google TV's setup app instead of the home screen; it now detects and offers to
  turn it off, with `undo.sh` recovery.
- Chinese picks Simplified or Traditional by the TV's script, then its region,
  and the home screen updates when only the region changes.

## 1.2.0 — 2026-10-19 (versionCode 8)

- Network icon by the clock: Wi-Fi with signal bars, Ethernet or offline, live;
  selecting it opens network settings.
- Optional Bluetooth, Sound and Picture shortcuts next to the clock.
- Uninstall and App info entries in the hold-OK menu.
- New `check.sh` to verify the setup and `--fix` it after TV updates.
- Safer `install.sh`: only known launchers are disabled, and it waits 15 seconds
  after turning one off.
- Fix: HDMI tiles work on Google TVs that hand HDMI to Google's input player.

## 1.1.0 — 2026-10-19 (versionCode 7)

- Hold OK (or Menu) on an app for Move, Replace and Remove from home; the app
  stays installed.
- New Add app tile to pick any installed app; changes are saved on the TV.
- Apps wrap into rows of six, up to twelve on screen, with vertical scrolling and
  four-direction navigation.
- Fix: HDMI tiles on TVs that ship Live TV under their own name (Amlogic,
  Droidlogic, some Epson projectors).

## 1.0.1 — 2026-10-18 (versionCode 6)

- First public release.
