import com.kotlinhero.starter.buildsrc.BuildConstants
import org.gradle.accessors.dm.LibrariesForLibs

// This is a part of a workaround to use version catalog inside buildSrc scripts.
// More on this: https://github.com/gradle/gradle/issues/15383
val libs = the<LibrariesForLibs>()

plugins {
    // libs (Version catalog) cannot be used here yet.
    // More on this: https://discuss.gradle.org/t/using-version-catalog-plugins-in-convention-plugins/45660
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    compileSdk = BuildConstants.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = BuildConstants.MIN_SDK_VERSION
        targetSdk = BuildConstants.TARGET_SDK_VERSION

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    flavorDimensions += "ENVIRONMENT"
    productFlavors {
        create("staging") {
            dimension = "ENVIRONMENT"
        }
        create("production") {
            dimension = "ENVIRONMENT"
        }
    }

    compileOptions {
        sourceCompatibility = BuildConstants.JAVA_VERSION
        targetCompatibility = BuildConstants.JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = BuildConstants.JVM_TARGET
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
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
    implementation(libs.bundles.compose.bundle)
    implementation(libs.androidx.material3)

    /*
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
     */
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation(libs.bundles.android.test.bundle)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.bundles.koin.bundle)
    ksp(libs.koin.annotations.compiler)

    implementation(libs.bundles.voyager.bundle)

    implementation(libs.bundles.ktor.bundle)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.jakewharton.timber)

    implementation(libs.bundles.datastore.bundle)
}
