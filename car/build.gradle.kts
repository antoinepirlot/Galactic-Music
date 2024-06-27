plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "io.github.antoinepirlot.satunes.car"
    compileSdk = 34

    defaultConfig {
        minSdk = 22

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildTypes {
        create("releaseTest") {
            initWith(getByName("release"))
        }
    }
}

dependencies {
    /**
     * Base
     */
    implementation("androidx.activity:activity-compose:1.9.0")

    /**
     * Media
     */
    val mediaVersion = "1.7.0"
    val media3Version = "1.3.1"
    implementation("androidx.media:media:$mediaVersion")
    implementation("androidx.media3:media3-common:$media3Version")

    /**
     * Playback
     */
    implementation(project(":playback"))

    /**
     * Database
     */
    implementation(project(":database"))

    /**
     * Icons
     */
    implementation(project(":icons"))

    /**
     * Utils
     */
    implementation(project(":utils"))
}