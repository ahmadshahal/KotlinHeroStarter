plugins {
    id("common-library-conventions")
}

android {
    namespace = "com.kotlinhero.starter.feature.settings"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":res"))
}