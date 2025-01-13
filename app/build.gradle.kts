plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.kotlinhero.starter"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.kotlinhero.starter"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Specify the same languages using the resourceConfigurations property
        // in your app's module-level build.gradle file
        resourceConfigurations += listOf("en", "ar")
    }

    androidResources {
        // To enable automatic per-app language support, follow these steps:
        // 1. To turn the feature on, use the generateLocaleConfig setting in the androidResources
        //    {} block of the module-level build.gradle.kts file
        //    (build.gradle file if you're using Groovy).
        //    The feature is off by default.
        // 2. Specify a default locale:
        //    In the app module's res folder, create a new file called
        //    resources.properties.
        //    In the resources.properties file, set the default locale with
        //    the unqualifiedResLocale label. To format the locale
        //    names, see How to form locale names.
        generateLocaleConfig = true
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

    flavorDimensions += "ENVIRONMENT"
    productFlavors {
        create("staging") {
            dimension = "ENVIRONMENT"
            applicationIdSuffix = ".staging"
            versionNameSuffix = "-staging"
        }
        create("production") {
            dimension = "ENVIRONMENT"
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK","true")
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.startup)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.navigation)

    implementation(libs.koin.annotations)
    ksp(libs.koin.annotations.compiler)

    implementation(libs.voyager.navigator)
    implementation(libs.voyager.transitions)
    implementation(libs.voyager.koin)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.jakewharton.timber)

    implementation(libs.androidx.security.crypto)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.androidx.biometric)
    implementation(libs.androidx.biometric.ktx)
}