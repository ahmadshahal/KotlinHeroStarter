
// This is a part of a workaround to use version catalog inside buildSrc scripts.
// More on this: https://github.com/gradle/gradle/issues/15383
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
