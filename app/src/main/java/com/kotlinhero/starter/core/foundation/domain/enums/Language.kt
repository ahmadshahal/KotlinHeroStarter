package com.kotlinhero.starter.core.foundation.domain.enums

import androidx.annotation.StringRes
import com.kotlinhero.starter.R
import java.util.Locale

enum class Language(val code: String, @StringRes val titleResId: Int) {
    Arabic("ar", R.string.arabic),
    English("en", R.string.english);

    companion object {
        fun fromLanguageCode(locale: Locale) = Language.entries.firstOrNull {
            it.code == locale.language.split("-").firstOrNull()
        } ?: English
    }
}