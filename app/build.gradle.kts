plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.jbsb4"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.jbsb4"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.6.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
//    implementation(files("C:\\Users\\Sarween\\AndroidStudioProjects\\JBSB4\\app\\libs\\jtds-1.3.1.jar"))
//    implementation("androidx.room:room-compiler:2.5.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("de.hdodenhof:circleimageview:3.1.0")
//    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    // Libraries
//    implementation("com.android.support:design:33.0.0")
//    implementation("com.github.d-max:spots-dialog:1.1@aar")
//    implementation ("com.github.d-max:spots-dialog:1.1@aar")
    //Retrofit
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    //RxJava
    implementation("io.reactivex.rxjava2:rxandroid:2.0.1")
    implementation("io.reactivex.rxjava2:rxjava:2.1.7")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
    // PDF
//    implementation("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
    // Graph
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
//    implementation("com.google.android.gms:play-services-maps:18.1.0")
//    implementation("com.google.android.gms:play-services-location:21.0.1")

//    implementation("io.ktor:ktor-client-android:1.5.0")
//    implementation("io.ktor:ktor-client-core:2.1.1")
//    implementation("io.ktor:ktor-client-gson:2.1.1")
//    implementation("io.ktor:ktor-client-json:2.1.1")
//    implementation("io.ktor:ktor-client-core:1.5.0")
//    implementation("io.ktor:ktor-client-gson:1.5.0")
//    implementation("io.ktor:ktor-client-json:1.5.0")
//    implementation("io.ktor:ktor-client-logging-jvm:1.5.0")
//    implementation("io.ktor:ktor-client-serialization:1.5.0")
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
//    implementation("com.google.dagger:hilt-android:2.44")
//    implementation("com.google.dagger:hilt-android-compiler:2.44")
//    implementation("io.coil-kt:coil-compose:2.2.2")



}