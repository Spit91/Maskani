package com.spit91.maskani.presentation.auth

import dagger.hilt.android.lifecycle.HiltViewModel
import com.spit91.maskani.domain.model.repository.usecase.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

@HiltViewModel
class SignInViewModel @Inject constructor(private val signInUseCase: SignInUseCase): ViewModel(){
    // A hidden, changeable stateFlow pipeline that only this ViewModel can modify
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    fun onEmailChanged(newValue: String) {
        _state.update { it.copy(email = newValue, errorMessage = null) }
    }
    fun onPasswordChanged(newValue: String) {
        _state.update { it.copy(password = newValue, errorMessage = null) }
    }
    /**
     * Called by the UI when the user taps the "Sign In" button.
     */
    fun signIn() {
        val currentEmail = _state.value.email
        val currentPassword = _state.value.password

        // Step A: Update the checklist to show we are loading
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        // Step B: Launch an asynchronous background worker thread (Coroutine)
        viewModelScope.launch {
            // Send credentials down into our Domain UseCase contract
            val result = signInUseCase(currentEmail, currentPassword)

            // Step C: Check the results and update the checklist accordingly
            result.onSuccess { authUser ->
                _state.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.localizedMessage ?: "An unexpected authentication error occurred."
                    )
                }
            }
        }
    }


}