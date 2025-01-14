package com.kotlinhero.starter.settings

import com.kotlinhero.starter.core.foundation.domain.enums.Language
import com.kotlinhero.starter.core.foundation.utils.localization.LocalizationUtils
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LanguageViewModelTest {

    private lateinit var languageViewModel: com.kotlinhero.starter.settings.presentation.viewmodels.LanguageViewModel

    @Before
    fun setup() {
        languageViewModel =
            com.kotlinhero.starter.settings.presentation.viewmodels.LanguageViewModel()
    }

    @Test
    fun `test onChangeLanguage to arabic, language should be changed correctly`() = runTest {
        // When
        languageViewModel.onLanguageChange(Language.Arabic)

        // Then
        Assert.assertTrue(languageViewModel.state.value.selectedLanguage == Language.Arabic)
        Assert.assertTrue(LocalizationUtils.getSelectedLanguage() == Language.Arabic)
    }

    @Test
    fun `test onChangeLanguage to english, language should be changed correctly`() = runTest {
        // When
        languageViewModel.onLanguageChange(Language.English)

        // Then
        Assert.assertTrue(languageViewModel.state.value.selectedLanguage == Language.English)
        Assert.assertTrue(LocalizationUtils.getSelectedLanguage() == Language.English)
    }
}