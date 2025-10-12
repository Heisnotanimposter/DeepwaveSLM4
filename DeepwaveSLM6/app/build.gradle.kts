plugins {
    id("com.android.application")
    id("com.chaquo.python")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.example.deepwaveslm"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.5.31")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-ktx:1.3.1")
    implementation("com.chaquo.python:gradle:10.0.0")
}

python {
    pip {
        install("tensorflow")
        install("transformers")
        install("torch")
    }
}