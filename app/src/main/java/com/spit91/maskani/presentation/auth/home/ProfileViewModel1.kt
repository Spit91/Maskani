package com.spit91.maskani.presentation.auth.home

import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.spit91.maskani.domain.model.User
import androidx.lifecycle.viewmodel.compose.viewModel

@HiltViewModel
class ProfileViewModel @Inject constructor (): ViewModel() {
    // internal mutable state(only this viewmodel can write updates here)
    private val _state = MutableStateFlow(ProfileState())
    // public read only state exposed to the UI screen
    val state: StateFlow<ProfileState> = _state.asStateFlow()

    init {
        //automatically load data when this viewmodel is first initialized
        loadUserProfile()
    }

    private fun loadUserProfile() {
        _state.update{ it.copy(isLoading = true)}

        //coroutine scope: bounds lifecycle of your background operations
        viewModelScope.launch {
            // simulating a minor network/ database delay like a real app would have
            delay(800)

            val mockUser = User (
                name = "Jessie",
                email = "jessiemwangi1311@gmail.com",
                contact = "+254 757 293 135"
            )
            _state.update{
                it.copy(
                    isLoading = false,
                    user = mockUser
                )
            }
        }
    }
    // Action to open or close the confirmation popup
    fun setLogoutDialogVisible(visible: Boolean){
        _state.update { it.copy(showLogoutDialog = visible)}

    }
    //Action to execute final logout sequence
    fun confirmLogout(){
        viewModelScope.launch(){
            // dismiss the popup dialog first
            _state.update {it.copy(showLogoutDialog = false, isLoading = true)}

            delay(500)

            _state.update{it.copy(isLoading = false, isLoggedOut = true)}
        }
    }

    fun onEditProfileClick (){
        _state.update {currentState ->
            currentState.copy(isEditingProfile = true)
        }
    }

    fun resetEditProfileState(){
        _state.update {currentState ->
            currentState.copy(isEditingProfile = false)
        }
    }


}