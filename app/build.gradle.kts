plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.bearmod"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.bearmod"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            // These values are used when building locally
            // For CI/CD, the values are provided by GitHub secrets
            storeFile = file(System.getenv("KEYSTORE_FILE") ?: "../keystore/bearmod.keystore")
            storePassword = System.getenv("KEY_STORE_PASSWORD") ?: "bearmod123"
            keyAlias = System.getenv("KEY_ALIAS") ?: "bearmod"
            keyPassword = System.getenv("KEY_PASSWORD") ?: "bearmod123"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}