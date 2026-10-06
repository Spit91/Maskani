package com.spit91.maskani.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.spit91.maskani.domain.model.repository.usecase.SignUpUseCase
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase): ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onNameChange(newValue: String) {
        _state.update{ it.copy(name = newValue,errorMessage = null)}
    }
    fun onEmailChange(newValue: String) {
        _state.update{ it.copy(email = newValue, errorMessage = null)}
    }
    fun onPasswordChanged(newValue: String){
        _state.update{ it.copy(password = newValue, errorMessage = null)}
    }
    fun onConfirmPasswordChanged(newValue: String) {
        _state.update { it.copy (confirmPassword = newValue, errorMessage = null )}
    }

    fun signUp(){
        val currentName = _state.value.name
        val currentEmail = _state.value.email
        val currentPassword = _state.value.password
        val currentConfirmPassword = _state.value.confirmPassword

        // Validate passwords
        if (currentPassword != currentConfirmPassword) {
            _state.update { it.copy ( isLoading = false, errorMessage = "Passwords do not match")}
        } else {
            _state.update { it.copy(isLoading = false, errorMessage = null)}
        }

        _state.update{ it.copy(isLoading = true, errorMessage = null)}

        viewModelScope.launch {
            val result = signUpUseCase(currentEmail, currentPassword, currentName, currentConfirmPassword)

            result.onSuccess {authUser ->
                _state.update{ it.copy(isLoading = false, isSuccess = true)}
            }
                .onFailure{exception ->
                    _state.update{
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.localizedMessage ?:"An unexpected error occurred while creating your account"
                        )
                    }
                }
        }
    }

}