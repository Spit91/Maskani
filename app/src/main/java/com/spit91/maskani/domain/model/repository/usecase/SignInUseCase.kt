package com.spit91.maskani.domain.model.repository.usecase
import javax.inject.Inject
import com.spit91.maskani.domain.model.AuthUser
import com.spit91.maskani.domain.model.repository.AuthRepository

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    suspend operator fun invoke(email:String, password:String): Result<AuthUser> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }
        else{
            return authRepository.signInWithEmail(email,password)
        }
    }
}