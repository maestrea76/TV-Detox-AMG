<div align="center">

# TV Detox AMG

[English](README.md) · [Español](README.es.md) · [Upstream project](https://github.com/Ansub/tv-detox)

### A calmer, cleaner home screen for Android TV.

TV Detox AMG is a community fork of [TV Detox](https://github.com/Ansub/tv-detox), keeping its simple remote-first controls while exploring a Clean Xiaomi TV-inspired look.

[![Build and test](https://img.shields.io/github/actions/workflow/status/maestrea76/TV-Detox-AMG/build.yml?branch=main&label=build%20%26%20tests)](https://github.com/maestrea76/TV-Detox-AMG/actions)
[![License](https://img.shields.io/badge/license-MIT-black)](LICENSE)

<img src="docs/screenshots/smoke-api28.png" alt="TV Detox AMG on an Android TV emulator (API 28)" width="100%">

</div>

## What’s new in this fork

- Glass-style surfaces and focus states across app, system and HDMI cards. The remote controls, app actions and HDMI inputs remain part of the original simple launcher workflow.
- A large clock, localized date and greeting, plus a weather line from Open-Meteo. Settings → Language switches the home screen labels between English and Spanish; otherwise they follow the TV. Weather offers three modes: Small shows the icon and temperature, Compact adds the city, and Large adds the conditions, feels-like temperature, humidity and wind. Choose location searches for a city and saves it. The search sits at the top of the screen, so the keyboard does not cover Search, Cancel or the results. Until you pick one, the line shows Getaria.
- A compact now-playing pill appears when Android reports active media. Selecting it returns to the player. Android may ask you to enable notification access if the installer could not grant it automatically.
- Network status remains at the top right. App tiles, system shortcuts and HDMI inputs are still navigated with the TV remote.
- Lightweight, color-gradient wallpapers; no photographic background assets are required.

The screenshots below were captured by the automated Android TV emulator tests. The available app tiles differ between emulator images and real televisions.

| Android TV emulator · API 28 | Android TV emulator · API 30 |
|---|---|
| ![TV Detox AMG screenshot from Android TV emulator API 28](docs/screenshots/smoke-api28.png) | ![TV Detox AMG screenshot from Android TV emulator API 30](docs/screenshots/smoke-api30.png) |

## Try it

Download the **tvhome-apk** artifact from a successful [GitHub Actions run](https://github.com/maestrea76/TV-Detox-AMG/actions/workflows/build.yml). Open the run, download and extract the artifact, then install `tvhome.apk` on your Android TV. This is a CI test build, not a signed GitHub Release. Builds from this fork are signed with a different key than upstream, so Android may require uninstalling an existing launcher copy before installing it; removing the app does not undo any separate debloating changes.

To install from a computer with Android Platform Tools:

```sh
adb connect <TV_IP>:5555
adb install -r tvhome.apk
```

If your TV uses Wireless debugging, pair and connect with the ports shown on the TV’s Wireless debugging screen. After installation, choose TV Detox AMG as the Home app if Android asks.

> **Safety:** Android TV settings and behavior vary by manufacturer. Keep the TV’s screensaver enabled, especially on OLED displays. Don’t disable system apps unless you understand how to restore them. See the [upstream guide](https://github.com/Ansub/tv-detox#help) for device setup, troubleshooting and undo instructions.

## Build from source

Requires Java 17 and Android SDK platform 34/build-tools 34.0.0.

```sh
sh build.sh
```

The APK is written to `out/tvhome.apk`. The repository’s GitHub Actions workflow builds it and runs smoke tests on Android TV emulators (API 28 and 30). The test screenshots are saved in [`docs/screenshots`](docs/screenshots/).

## About this fork

TV Detox AMG is an independent fork and is not affiliated with the upstream project’s author. It retains the upstream MIT license and builds on its launcher and TV-cleanup scripts. See [LICENSE](LICENSE) and the [upstream repository](https://github.com/Ansub/tv-detox).
