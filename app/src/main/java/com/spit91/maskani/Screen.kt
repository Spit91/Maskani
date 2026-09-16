package com.spit91.maskani

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object PropertyDetails : Screen("property/{propertyId}")
}
