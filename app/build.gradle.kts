plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        composeOptions{
            kotlinCompilerExtensionVersion = "1.5.2"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        // Remove kotlinCompilerExtensionVersion as it's managed by the BOM
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1") // Check for newer version
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5") // Check for newer version
    implementation("androidx.activity:activity-compose:1.9.2") // Check for newer version
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation(platform("androidx.compose:compose-bom:2024.09.00")) // Update to latest BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation(platform("com.google.firebase:firebase-bom:33.2.0")) // Use Firebase BOM
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
    implementation("androidx.media3:media3-common:1.4.1") // Use -ktx version
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.code.gson:gson:2.11.0")
    implementation("androidx.navigation:navigation-compose:2.8.0") // Check for newer version
    implementation("io.coil-kt:coil-compose:2.5.0")
}