package com.spit91.maskani.presentation.auth.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spit91.maskani.domain.model.repository.usecase.GetSavedPropertiesUseCase
import com.spit91.maskani.domain.model.repository.usecase.ToggleSavedPropertyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val getSavedPropertiesUseCase: GetSavedPropertiesUseCase,
    private val toggleSavedPropertyUseCase: ToggleSavedPropertyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SavedState())
    val state: StateFlow<SavedState> = _state.asStateFlow()

    fun loadSaved(showSpinner: Boolean = false) {
        if (showSpinner) {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
        }

        viewModelScope.launch {
            getSavedPropertiesUseCase()
                .onSuccess { properties ->
                    _state.update {
                        it.copy(isLoading = false, properties = properties, errorMessage = null)
                    }
                }
                .onFailure { exception ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.localizedMessage
                                ?: "Could not load your saved listings"
                        )
                    }
                }
        }
    }

    fun unsave(propertyId: String) {
        _state.update { current ->
            current.copy(properties = current.properties.filterNot { it.id == propertyId })
        }

        viewModelScope.launch {
            toggleSavedPropertyUseCase(propertyId, currentlySaved = true)
                .onFailure { _ ->
                    _state.update { it.copy(transientMessage = "Could not remove this listing") }
                    loadSaved()
                }
        }
    }

    fun onMessageShown() {
        _state.update { it.copy(transientMessage = null) }
    }
}