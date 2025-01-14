import com.kotlinhero.starter.buildsrc.BuildConstants

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.kotlinhero.starter"
    compileSdk = BuildConstants.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildConstants.APPLICATION_ID
        minSdk = BuildConstants.MIN_SDK_VERSION
        targetSdk = BuildConstants.TARGET_SDK_VERSION

        versionCode = BuildConstants.VERSION_CODE
        versionName = BuildConstants.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Replace with your app's name to use it as a default,
        signingConfig = signingConfigs.getByName("debug")

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
            isMinifyEnabled = true
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

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":navigation"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:settings"))
    implementation(project(":res"))

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

    testImplementation(libs.testing.mockito)
    testImplementation(libs.testing.mockito.inline)
    testImplementation(libs.testing.mockito.kotlin)
    testImplementation(libs.testing.androidx.core)
    testImplementation(libs.testing.androidx.junit)
    testImplementation(libs.testing.coroutines)
    testImplementation(libs.androidx.test.rules)
    testImplementation(libs.androidx.test.runner)

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

    implementation(libs.androidx.core.splashscreen)
}