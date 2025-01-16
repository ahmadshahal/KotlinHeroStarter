plugins {
    id("common-library-conventions")
}

android {
    namespace = "com.kotlinhero.starter.core"
}

dependencies {
    implementation(project(":res"))

    implementation(libs.androidx.security.crypto)
}