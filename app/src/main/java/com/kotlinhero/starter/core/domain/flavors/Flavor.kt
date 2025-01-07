package com.kotlinhero.starter.core.domain.flavors

import com.kotlinhero.starter.BuildConfig

enum class Flavor(
    val key: String,
    val baseUrl: String,
) {
    STAGING(
        key = "staging",
        baseUrl = "https://dev-backend.app",
    ),
    PRODUCTION(
        key = "production",
        baseUrl = "https://backend.app",
    );

    companion object {
        fun current() = Flavor.entries.first {
            it.key == BuildConfig.FLAVOR
        }
    }
}
