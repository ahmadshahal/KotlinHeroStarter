import com.kotlinhero.starter.buildsrc.BuildConstants

plugins {
    // id declaration is used instead of alias because of the usage of buildSrc
    // Using alias with buildSrc isn't supported yet.
    // More on this: https://github.com/gradle/gradle/issues/20084
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.ksp.get().pluginId)
    id(libs.plugins.compose.compiler.get().pluginId)
    id(libs.plugins.jetbrainsKotlinSerialization.get().pluginId)
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
    implementation(libs.bundles.datastore.bundle)

    implementation(libs.jakewharton.timber)

    implementation(libs.androidx.security.crypto)
    implementation(libs.bundles.biometric.bundle)

    implementation(libs.androidx.core.splashscreen)
}