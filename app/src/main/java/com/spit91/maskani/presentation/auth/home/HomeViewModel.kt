package com.spit91.maskani.presentation.auth.home

import com.spit91.maskani.domain.model.repository.usecase.GetPublishedPropertiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.spit91.maskani.domain.model.repository.usecase.GetSavedPropertyIdsUseCase
import com.spit91.maskani.domain.model.repository.usecase.ToggleSavedPropertyUseCase

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPublishedPropertiesUseCase: GetPublishedPropertiesUseCase,
    private val getSavedPropertyIdsUseCase: GetSavedPropertyIdsUseCase,
    private val toggleSavedPropertyUseCase: ToggleSavedPropertyUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init{
        loadProperties()
    }
    fun loadProperties() {
        _state.update { it.copy(isLoading = true, errorMessage = null)}
        viewModelScope.launch {
            getPublishedPropertiesUseCase().onSuccess{ properties ->
                _state.update { it.copy(isLoading = false, properties = properties )}
            }
                .onFailure{exception ->
                    _state.update {
                        it.copy(isLoading = false,
                            errorMessage = exception.localizedMessage?:"Could not load listings"
                        )
                    }
                }
        }
    }

    // Find out which listings the user has saved, so the right hearts are filled

    fun loadSavedIds() {
        viewModelScope.launch {
            getSavedPropertyIdsUseCase().onSuccess { ids ->
                _state.update { it.copy(savedIds = ids.toSet())}
            }
        }
    }
    fun toggleSaved(propertyId: String) {
        val wasSaved = propertyId in _state.value.savedIds

        //Flip the heart straight away, then confirm with firestore
        _state.update{ current ->
            current.copy(
                savedIds = if(wasSaved) current.savedIds - propertyId
                else current.savedIds + propertyId
            )
        }

        viewModelScope.launch {
            toggleSavedPropertyUseCase(propertyId, wasSaved)
                .onFailure { _ -> //ignore the error and flip the heart back
                    _state.update {current ->
                        current.copy(
                            savedIds = if (wasSaved) current.savedIds + propertyId
                            else current.savedIds - propertyId,
                            transientMessage = "Could not update your saved listings"
                        )
                    }

                }
        }
    }
    fun onMessageShown() {
        _state.update{ it.copy(transientMessage = null)}
    }
    fun onSearchQueryChange(newValue: String) {
        _state.update{ it.copy(searchQuery = newValue)}
    }
    fun onUnitTypeSelected(unitType: String?) {
        _state.update { it.copy(selectedUnitType = unitType)}
    }
    fun onLocationSelected( location: String?) {
        _state.update { it.copy(selectedLocation = location)}
    }
    fun onPriceRangeSelected( priceRange: PriceRange?) {
        _state.update { it.copy(selectedPriceRange = priceRange)}
    }

    fun clearFilters() {
        _state.update {
            it.copy(
                searchQuery = "",
                selectedUnitType= null,
                selectedLocation = null,
                selectedPriceRange = null
            )
        }
    }
}