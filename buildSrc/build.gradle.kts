plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

// This is a part of a workaround to add a plugin as a library using version catalog.
// More on this: https://github.com/gradle/gradle/issues/17963
private fun Provider<PluginDependency>.text(): String {
    val t = get()
    val id = t.pluginId
    val version = t.version
    return "$id:$id.gradle.plugin:$version"
}

dependencies {
    // This is a part of a workaround to use version catalog inside buildSrc scripts.
    // More on this: https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // All plugins used in buildSrc scripts should have a declaration here.
    implementation(libs.plugins.android.library.text())
    implementation(libs.plugins.kotlin.android.text())
    implementation(libs.plugins.ksp.text())
    implementation(libs.plugins.compose.compiler.text())
    implementation(libs.plugins.jetbrainsKotlinSerialization.text())
}