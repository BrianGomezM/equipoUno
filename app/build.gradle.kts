plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.univalle.picobotellamvvm"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.univalle.picobotellamvvm"
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

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    val navVersion = "2.5.3"
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")



    //testing
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation ("androidx.navigation:navigation-ui-ktx:$navVersion")
    implementation("androidx.navigation:navigation-common:$navVersion")

    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    //corrutinas
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    //viewModel
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // LiveData
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")

    // Activity
    implementation("androidx.activity:activity-ktx:1.6.1")

    // Fragment
    implementation("androidx.fragment:fragment-ktx:1.5.5")

    // Room
    implementation ("androidx.room:room-runtime:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")



    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    //Toolbar
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.drawerlayout:drawerlayout:1.2.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
}