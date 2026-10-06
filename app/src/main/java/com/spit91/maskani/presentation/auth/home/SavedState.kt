package com.spit91.maskani.presentation.auth.home

import com.spit91.maskani.domain.model.Property

data class SavedState(
    val isLoading: Boolean = true,
    val properties: List<Property> = emptyList(),
    val errorMessage: String? = null,
    val transientMessage: String? = null
)