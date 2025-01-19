package com.kotlinhero.starter.presentation.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kotlinhero.starter.core.presentation.components.StarterBottomNavigationBar
import com.kotlinhero.starter.core.presentation.components.StarterNavigationBarItem
import com.kotlinhero.starter.core.presentation.theme.starterTypography
import com.kotlinhero.starter.features.auth.presentation.navigation.AuthDestinations
import com.kotlinhero.starter.res.R
import kotlinx.serialization.Serializable

private sealed class MainDestinations {
    @Serializable
    data object Home : MainDestinations()

    @Serializable
    data object Profile : MainDestinations()
}

@Composable
fun MainScreen(navController: NavController) {
    val bottomNavigationController = rememberNavController()

    val navBackStackEntry by bottomNavigationController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Box(modifier = Modifier.fillMaxSize()) {
        MainNavHost(
            navController = bottomNavigationController,
            parentNavController = navController
        )
        StarterBottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            currentDestination = currentDestination,
            onItemSelected = { route ->
                bottomNavigationController.navigate(route) {
                    popUpTo(0) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Composable
private fun MainNavHost(navController: NavHostController, parentNavController: NavController) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = MainDestinations.Home
    ) {
        composable<MainDestinations.Home> {
            HomeScreen(navController = parentNavController)
        }
        composable<MainDestinations.Profile> {
            ProfileScreen(
                navController = parentNavController,
                onLogout = {
                    parentNavController.navigate(AuthDestinations.Auth) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

@Composable
private fun StarterBottomNavigationBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onItemSelected: (Any) -> Unit
) {
    StarterBottomNavigationBar(modifier = modifier) {
        NavigationItem(
            route = MainDestinations.Home,
            label = R.string.home,
            selected = currentDestination?.hasRoute(MainDestinations.Home::class) == true,
            selectedIcon = R.drawable.ic_nav_home_selected,
            unselectedIcon = R.drawable.ic_nav_home,
            onClick = onItemSelected
        )
        NavigationItem(
            route = MainDestinations.Profile,
            label = R.string.chat,
            selected = false,
            selectedIcon = R.drawable.ic_nav_chat_selected,
            unselectedIcon = R.drawable.ic_nav_chat,
            onClick = {}
        )
        NavigationItem(
            route = MainDestinations.Profile,
            label = R.string.profile,
            selected = currentDestination?.hasRoute(MainDestinations.Profile::class) == true,
            selectedIcon = R.drawable.ic_nav_profile_selected,
            unselectedIcon = R.drawable.ic_nav_profile,
            onClick = onItemSelected
        )
    }
}

@Composable
private fun RowScope.NavigationItem(
    route: Any,
    @StringRes label: Int,
    selected: Boolean,
    @DrawableRes selectedIcon: Int,
    @DrawableRes unselectedIcon: Int,
    onClick: (Any) -> Unit
) {
    StarterNavigationBarItem(
        selected = selected,
        onClick = { onClick(route) },
        icon = {
            val icon = if (selected) selectedIcon else unselectedIcon
            Image(
                painter = painterResource(icon),
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(label),
                style = MaterialTheme.starterTypography.body14Medium
            )
        }
    )
}