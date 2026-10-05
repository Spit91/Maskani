package com.spit91.maskani.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.spit91.maskani.domain.model.repository.usecase.ForgotPasswordUseCase
import com.spit91.maskani.presentation.auth.ForgotPasswordState
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope


@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val forgotPasswordUseCase: ForgotPasswordUseCase) : ViewModel() {

        private val _state = MutableStateFlow(ForgotPasswordState())
        val state: StateFlow<ForgotPasswordState> = _state.asStateFlow()

        fun onEmailChange(newValue: String) {
            _state.update { it.copy(email = newValue, errorMessage = null) }
        }

    fun sendPasswordResetEmail() {
        val currentEmail = _state.value.email

        _state.update {it.copy(isLoading = true, errorMessage = null)}

        viewModelScope.launch {
            val result = forgotPasswordUseCase(currentEmail)

            result.onSuccess {
                _state.update { it.copy(isLoading = false, isEmailSent = true) }
            }
             .onFailure { exception ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.localizedMessage ?:"Couldn't send reset email. Please check the address and try again"
                    )
                }
            }
        }
    }


    }