package com.kotlinhero.starter.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import com.kotlinhero.starter.core.foundation.presentation.components.StarterBottomNavigationBar
import com.kotlinhero.starter.core.foundation.presentation.components.StarterNavigationBarItem
import com.kotlinhero.starter.core.foundation.presentation.theme.starterTypography
import com.kotlinhero.starter.res.R

class MainScreen : Screen {

    @Composable
    override fun Content() {
        Navigator(screen = HomeScreen()) { navigator ->
            FadeTransition(navigator)
            Box(modifier = Modifier.fillMaxSize()) {
                CurrentScreen()
                StarterBottomNavigationBar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    val isHome = navigator.lastItemOrNull is HomeScreen
                    StarterNavigationBarItem(
                        selected = isHome,
                        onClick = {
                            navigator.replace(HomeScreen())
                        },
                        icon = {
                            val icon =
                                if (isHome) R.drawable.ic_nav_home_selected else R.drawable.ic_nav_home
                            Image(
                                painter = painterResource(icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.home),
                                style = MaterialTheme.starterTypography.body14Medium
                            )
                        }
                    )

                    StarterNavigationBarItem(
                        selected = false,
                        onClick = { },
                        icon = {
                            // val icon = if (false) R.drawable.ic_nav_chat_selected else R.drawable.ic_nav_chat
                            val icon = R.drawable.ic_nav_chat
                            Image(
                                painter = painterResource(icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.chat),
                                style = MaterialTheme.starterTypography.body14Medium
                            )
                        }
                    )

                    val isProfile = navigator.lastItemOrNull is ProfileScreen
                    StarterNavigationBarItem(
                        selected = isProfile,
                        onClick = {
                            navigator.replace(ProfileScreen())
                        },
                        icon = {
                            val icon =
                                if (isProfile) R.drawable.ic_nav_profile_selected else R.drawable.ic_nav_profile
                            Image(
                                painter = painterResource(icon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(R.string.profile),
                                style = MaterialTheme.starterTypography.body14Medium
                            )
                        }
                    )
                }
            }
        }
    }
}