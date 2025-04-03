# Keystore for App Signing

This directory is used for storing keystore files for signing the Android application.

## Using an Existing Keystore

If you already have a keystore file (like `BearOwner.jks`), you can use the provided PowerShell script to prepare it for GitHub Actions:

```powershell
# Run from the project root directory
.\scripts\prepare-keystore.ps1 -KeystorePath "C:\path\to\your\keystore.jks" -KeyAlias "your_key_alias" -KeystorePassword "your_keystore_password" -KeyPassword "your_key_password"
```

## Creating a New Keystore

If you need to create a new keystore, you can use the Android Studio or the `keytool` command:

```bash
keytool -genkeypair -v -keystore bearmod.keystore -alias bearmod -keyalg RSA -keysize 2048 -validity 10000 -storepass bearmod123 -keypass bearmod123
```

## GitHub Secrets Configuration

After preparing your keystore, you need to add the following secrets to your GitHub repository:

1. `SIGNING_KEY`: The base64-encoded keystore file
2. `KEY_ALIAS`: The alias of the key in the keystore
3. `KEY_STORE_PASSWORD`: The password for the keystore
4. `KEY_PASSWORD`: The password for the key

These secrets are used by the GitHub Actions workflow to sign your app during the build process.

## Local Development

For local development, you can place your keystore file in this directory and update the `signingConfigs` section in `app/build.gradle.kts` with your keystore details.

**Note:** Never commit your actual keystore files or passwords to the repository. The `.gitignore` file is configured to exclude `.keystore` and `.jks` files from version control.
