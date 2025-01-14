plugins {
    // id declaration is used instead of alias because of the usage of buildSrc.
    // Using alias with buildSrc isn't supported yet.
    // More on this: https://github.com/gradle/gradle/issues/20084
    id(libs.plugins.android.application.get().pluginId) apply false
    id(libs.plugins.android.library.get().pluginId) apply false
    id(libs.plugins.kotlin.android.get().pluginId) apply false
    id(libs.plugins.ksp.get().pluginId) apply false
    id(libs.plugins.compose.compiler.get().pluginId) apply false
    id(libs.plugins.jetbrainsKotlinSerialization.get().pluginId) apply false
}