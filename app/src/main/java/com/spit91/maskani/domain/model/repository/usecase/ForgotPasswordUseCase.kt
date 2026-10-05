package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.repository.AuthRepository
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        if (email.isBlank()) {
            return Result.failure(IllegalArgumentException("Please enter your email address"))
        }
        return authRepository.sendPasswordResetEmail(email)
    }
}