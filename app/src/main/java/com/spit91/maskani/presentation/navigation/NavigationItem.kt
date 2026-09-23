package com.spit91.maskani.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person



sealed class NavigationItem(
    val route: Screen,
    val title: String,
    val icon: ImageVector
) {
    data object Home : NavigationItem(Screen.Home, "Explore", Icons.Filled.Home)
    data object Saved : NavigationItem(Screen.Saved, "Saved", Icons.Filled.Favorite)
    data object Profile : NavigationItem(Screen.Profile, "Profile", Icons.Filled.Person)

}
val bottomNavItems = listOf(
    NavigationItem.Home,
    NavigationItem.Saved,
    NavigationItem.Profile
)