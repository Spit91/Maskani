package com.spit91.maskani.presentation.auth.home

import com.spit91.maskani.domain.model.User
data class ProfileState (
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val showLogoutDialog: Boolean = false,
    val isLoggedOut: Boolean = false,
    val isEditingProfile: Boolean = false
)