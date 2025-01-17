plugins {
    id("common-library-conventions")
}

android {
    namespace = "com.kotlinhero.starter.features.settings"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":res"))
}