package com.kotlinhero.starter.settings.features

import com.kotlinhero.starter.core.domain.enums.Language
import com.kotlinhero.starter.core.utils.localization.LocalizationUtils
import com.kotlinhero.starter.features.settings.presentation.viewmodels.LanguageViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LanguageViewModelTest {

    private lateinit var languageViewModel: LanguageViewModel

    @Before
    fun setup() {
        languageViewModel = LanguageViewModel()
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