plugins {
    id("common-library-conventions")
}

android {
    namespace = "com.kotlinhero.starter.feature.auth"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":res"))

}
