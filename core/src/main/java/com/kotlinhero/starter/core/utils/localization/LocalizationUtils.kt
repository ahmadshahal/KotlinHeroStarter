package com.kotlinhero.starter.core.utils.localization

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.kotlinhero.starter.core.domain.enums.Language
import java.util.Locale

object LocalizationUtils {
    fun changeLanguage(language: Language) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language.code)
        // Call this on the main thread as it may require Activity.restart()
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    fun getSelectedLanguage(): Language {
        val languageCode = AppCompatDelegate.getApplicationLocales().toLanguageTags()
        val locale = Locale(languageCode)
        return Language.fromLanguageCode(locale)
    }
}
