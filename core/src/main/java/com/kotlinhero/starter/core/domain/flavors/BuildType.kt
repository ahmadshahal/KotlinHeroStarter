package com.kotlinhero.starter.core.domain.flavors

import com.kotlinhero.starter.core.BuildConfig

enum class BuildType(val key: String) {
    DEBUG(key = "debug"),
    RELEASE(key = "release");

    companion object {
        fun current() = BuildType.entries.first {
            it.key == BuildConfig.BUILD_TYPE
        }
    }
}