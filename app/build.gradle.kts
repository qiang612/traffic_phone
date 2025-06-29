plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.rainkaze.traffic"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rainkaze.traffic"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // Retrofit & Gson for network requests and JSON parsing
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")// For logging network requests

    // RecyclerView for efficient list display
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Material Components for modern UI elements (like NavigationView)
    implementation("com.google.android.material:material:1.12.0")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // Chart library (e.g., MPAndroidChart)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}