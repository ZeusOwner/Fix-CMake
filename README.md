# BearMod Android App

## Build and Deployment Automation

This project uses GitHub Actions to automate the build, test, and deployment process. Automation has been implemented as a top priority to ensure code quality and facilitate continuous development.

### Implemented Workflows

#### 1. Continuous Integration (CI)
- **File**: `.github/workflows/android-ci.yml`
- **Purpose**: Automatically build the application on each push or pull request.
- **Features**:
  - Automatic setup of the development environment (JDK, Android SDK, NDK)
  - Dependency caching to speed up builds
  - Execution of the custom `autoBuild` task
  - Publication of build artifacts

#### 2. Automated Testing
- **File**: `.github/workflows/android-test.yml`
- **Purpose**: Run unit and instrumented tests.
- **Features**:
  - Execution of unit tests with JUnit
  - Execution of instrumented tests on an Android emulator
  - Publication of test results

#### 3. Automated Deployment
- **File**: `.github/workflows/android-release.yml`
- **Purpose**: Generate and publish application versions.
- **Features**:
  - Triggered when creating a tag with the format `v*` (e.g., v1.0.0)
  - Automatic APK signing
  - Creation of a new GitHub release with the signed APK

### Custom `autoBuild` Task

A custom Gradle task called `autoBuild` has been implemented that:

1. Verifies the development environment configuration
2. Cleans the project
3. Builds the debug version
4. Runs unit tests
5. Handles errors robustly

### How to Use the Automation

#### For Developers
1. Make your changes in a separate branch
2. Create a Pull Request to `main`
3. The automation will run the build and tests
4. Once approved and merged, the changes will go to `main`

#### To Create a New Version
1. Make sure all changes are in the `main` branch
2. Create a new tag with the format `v1.x.x`:
   ```bash
   git tag v1.0.0
   git push origin v1.0.0
   ```
3. The release workflow will be triggered automatically
4. A new release will be created on GitHub with the signed APK

## Keystore Setup for App Signing

### Using an Existing Keystore

If you already have a keystore file (like `BearOwner.jks`), you can use the provided PowerShell script to prepare it for GitHub Actions:

```powershell
# Run from the project root directory
.\scripts\prepare-keystore.ps1 -KeystorePath "C:\path\to\your\keystore.jks" -KeyAlias "your_key_alias" -KeystorePassword "your_keystore_password" -KeyPassword "your_key_password"
```

This script will encode your keystore file to base64 and provide instructions for setting up GitHub secrets.

### GitHub Secrets Configuration

For the automatic signing process to work, you must configure the following secrets in your GitHub repository:

- `SIGNING_KEY`: The signing key in Base64 format
- `KEY_ALIAS`: The key alias
- `KEY_STORE_PASSWORD`: The keystore password
- `KEY_PASSWORD`: The key password

### Local Development

For local development, you can place your keystore file in the `keystore` directory and update the `signingConfigs` section in `app/build.gradle.kts` with your keystore details.

**Note:** Never commit your actual keystore files or passwords to the repository. The `.gitignore` file is configured to exclude `.keystore` and `.jks` files from version control.

### Troubleshooting

If you encounter issues with the automation:

1. Check the execution logs in the "Actions" tab of GitHub
2. Make sure all necessary environment variables are configured
3. Verify that the APK signing secrets are correctly configured

---

With this implementation, the build, test, and deployment process is fully automated, allowing for faster and more reliable development.
