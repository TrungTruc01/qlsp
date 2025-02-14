plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize") // Thêm plugin Parcelize
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.qlsp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.qlsp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.database.ktx)

    // Firebase UI Database
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-database-ktx:20.5.1")
    implementation("com.firebaseui:firebase-ui-database:8.0.0")

    // RecyclerView and ConstraintLayout
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("com.google.android.material:material:1.6.1")
    implementation(libs.play.services.analytics.impl)

    // Các thư viện kiểm thử
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Firebase Realtime Database
    implementation("com.google.firebase:firebase-database:21.0.0")
    implementation("com.google.firebase:firebase-messaging:24.0.2")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
}
