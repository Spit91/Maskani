package com.spit91.maskani.presentation.auth.home.edit

data class EditProfileState(
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val nameInput: String = "",
    val contactInput: String = "",
    val errorMessage: String? = null,
    val isSavingSuccess: Boolean = false
)