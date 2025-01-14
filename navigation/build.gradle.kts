import com.kotlinhero.starter.buildsrc.BuildConstants

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.kotlinhero.starter.navigation"
    compileSdk = BuildConstants.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = BuildConstants.MIN_SDK_VERSION
        targetSdk = BuildConstants.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = BuildConstants.JVM_TARGET
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.koin)
}