package com.kotlinhero.starter.settings.presentation.states

import com.kotlinhero.starter.core.domain.enums.Language

data class LanguageState(
    val selectedLanguage: Language = Language.English,
)