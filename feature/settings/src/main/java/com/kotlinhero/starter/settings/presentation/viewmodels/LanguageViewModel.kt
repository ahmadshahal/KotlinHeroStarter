package com.kotlinhero.starter.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.kotlinhero.starter.core.domain.enums.Language
import com.kotlinhero.starter.core.utils.localization.LocalizationUtils
import com.kotlinhero.starter.settings.presentation.states.LanguageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LanguageViewModel : ViewModel() {

    private val mutableState = MutableStateFlow(LanguageState())
    val state: StateFlow<LanguageState> = mutableState

    init {
        val initialLanguage = LocalizationUtils.getSelectedLanguage()
        mutableState.update { it.copy(selectedLanguage = initialLanguage) }
    }

    fun onLanguageChange(language: Language) {
        mutableState.update { it.copy(selectedLanguage = language) }
        LocalizationUtils.changeLanguage(language)
    }
}