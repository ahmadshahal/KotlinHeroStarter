package com.kotlinhero.starter.features.settings.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.kotlinhero.starter.core.domain.enums.Language
import com.kotlinhero.starter.core.presentation.reusables.topbar.DefaultTopBar
import com.kotlinhero.starter.core.presentation.theme.starterColors
import com.kotlinhero.starter.res.R
import com.kotlinhero.starter.features.settings.presentation.components.LanguageItem
import com.kotlinhero.starter.features.settings.presentation.viewmodels.LanguageViewModel
import org.koin.androidx.compose.koinViewModel

class LanguageScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: LanguageViewModel = koinViewModel()
        val navigator = LocalNavigator.currentOrThrow

        val state by viewModel.state.collectAsStateWithLifecycle()

        Scaffold(
            topBar = {
                DefaultTopBar(
                    title = stringResource(R.string.language),
                    onNavigateUp = navigator::pop
                )
            },
            containerColor = MaterialTheme.starterColors.background,
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = innerPadding)
                    .verticalScroll(rememberScrollState()),
            ) {
                Language.entries.forEach {
                    LanguageItem(
                        language = it,
                        modifier = Modifier,
                        isChecked = it == state.selectedLanguage,
                        onClick = { viewModel.onLanguageChange(it) }
                    )
                    HorizontalDivider(
                        color = MaterialTheme.starterColors.neutrals200,
                    )
                }
            }
        }
    }
}