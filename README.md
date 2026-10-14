# Hydroshen

**Hydroshen — Android VPN client**  
Developer: **SHΞЯVIN™**

Native Android project scaffold for the Hydroshen VPN client. The Android app structure and automated debug-APK build are in place; the Windows VPN engine has not yet been ported into this repository, so this scaffold does **not** establish a working VPN tunnel yet.

## Build
- Open this repository in Android Studio, or
- Push to GitHub and download the **Hydroshen Android APK** artifact from the Actions run.

Requirements: JDK 17, Android SDK 35.

## Project layout
- `app/` — native Android app (Jetpack Compose)
- `.github/workflows/android.yml` — CI build and APK artifact upload

## Next porting step
Integrate the original VPN protocol/core using Android-compatible libraries and Android's `VpnService`; Windows-only drivers, services, and APIs must be replaced, not wrapped in a WebView.

---
© SHΞЯVIN™. Hydroshen.
