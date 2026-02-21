plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlinx.serialization)
//    alias(libs.plugins.ksp)
    id ("com.google.devtools.ksp") version libs.versions.kspVersion.get() // Updated KSP plugin version
    id ("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
}


val BASE_URL: String = project.findProperty("APP_BASE_URL") as? String ?: "http://localhost:5000/"



android {
    namespace = "com.dino.message"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sec.message"
        minSdk = 24
        targetSdk = 35
        versionCode = 10
        versionName = "1.6"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            buildConfigField("String", "BASE_URL", "\"$BASE_URL\"")
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
        buildConfig = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.13"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.swiperefreshlayout)
//    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.hilt.android)
    ksp (libs.hilt.compiler)
    // For Jetpack Compose integration
    implementation (libs.androidx.hilt.navigation.compose)

    // compose-destinations
    implementation (libs.core)
    implementation(libs.bottom.sheet)
    ksp(libs.ksp)

    implementation(libs.kotlinx.serialization.json)

    //Ktor
    implementation (libs.ktor.client.core)
    implementation (libs.ktor.client.cio)
    implementation (libs.ktor.client.content.negotiation)
    implementation (libs.ktor.serialization.kotlinx.json)
    implementation (libs.ktor.client.serialization)
    implementation (libs.ktor.client.logging)
    implementation (libs.logback.classic)

    implementation (libs.androidx.datastore.preferences)

    implementation(libs.accompanist.systemuicontroller)

    // Core ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.6.0")

    // UI for ExoPlayer
    implementation("androidx.media3:media3-ui:1.6.0")

    // Optional: Common media utilities
    implementation("androidx.media3:media3-common:1.6.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Gson
    implementation("com.google.code.gson:gson:2.10.1")

    // androidx.security for EncryptedSharedPreferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")
    implementation("androidx.security:security-identity-credential:1.0.0-alpha03")
    implementation("androidx.security:security-app-authenticator:1.0.0-beta01")

    // material icons
    implementation(libs.androidx.material.icons.extended)

    // foundation
    implementation(libs.androidx.compose.foundation)

    // room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

}