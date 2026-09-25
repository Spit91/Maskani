package com.spit91.maskani.presentation.auth.home.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import jakarta.inject.Inject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

@HiltViewModel
class EditProfileViewModel @Inject constructor(): ViewModel() {
    //private control panel where the ViewModel edits screen details
    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    init{
        loadCurrentProfileData()
    }
    private fun loadCurrentProfileData () {
        //tell the state snapshot that we are loading data from the database
        _state.update{ it.copy(isLoading = true)}
        //launch a background coroutine thread to simulate database lookups

        viewModelScope.launch {
            //mock database results
            //pretend this data came from an online server or local database room cache
            val currentLoginEmail = "jessiemwangi1311@gmail.com"
            val currentSavedName = ""
            val currentSavedContact = ""

            //executing your email fallback logic rule
            val preFilledName = if (currentSavedName.isBlank()) {
                currentLoginEmail // fallback to pre-fill the field with the email if username is missing
            } else {
                currentSavedName // otherwise, use the saved username
            }
           //push the final data snapshot to our stream window for the UI to display
           _state.update { currentState ->
               currentState.copy(
                   isLoading = false,
                   nameInput = preFilledName,
                   contactInput = currentSavedContact
               )
           }
        }
    }
    // trigger input every time the user types something
    fun onNameChange(newValue: String) {
        _state.update { it.copy(nameInput = newValue)}
    }
    fun onContactChange(newValue: String){
        _state.update {it.copy(contactInput = newValue)}
    }
    //save button
    fun SaveProfileChanges() {
        //coroutine scope: freeze input button and trigger loaders while background routine saves
        viewModelScope.launch {
            _state.update {it.copy(isSaving = true)}

            //simulate a network database save delay
            kotlinx.coroutines.delay(1000)

            // update state to confirm success so navigation routes can pop backwards
            _state.update{currentState ->
                currentState.copy(
                    isSaving = false,
                    isSavingSuccess = true
                )
            }
        }
    }
}