plugins {
    id("common-library-conventions")
}

android {
    namespace = "com.kotlinhero.starter.features.auth"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":res"))

    implementation(libs.androidx.security.crypto)
    implementation(libs.bundles.biometric.bundle)
}
