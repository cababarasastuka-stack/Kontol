# SessionManager Android

Cloud-buildable Android project. The GitHub Actions workflow builds a debug APK automatically.

## Build from an Android phone

1. Create a GitHub repository.
2. Upload the contents of this folder to the repository.
3. Open the **Actions** tab and select **Build Android APK**.
4. Press **Run workflow** if the workflow did not start automatically.
5. Open the completed run and download the artifact named **SessionManager-debug-apk**.
6. Extract the artifact ZIP and install `app-debug.apk` on Android.

The workflow uses Java 17, Gradle 8.10, and Android Gradle Plugin 8.6.1.
