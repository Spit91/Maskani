package com.spit91.maskani

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable data object Home : Screen
    @Serializable data class PropertyDetails (val propertyId: Int) : Screen
}
