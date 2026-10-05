package com.spit91.maskani.domain.model.repository.usecase

import com.spit91.maskani.domain.model.AuthUser
import com.spit91.maskani.domain.model.UserProfile
import com.spit91.maskani.domain.model.repository.AuthRepository
import com.spit91.maskani.domain.model.repository.UserRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email: String, password: String, name: String): Result<AuthUser> {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            return Result.failure(IllegalArgumentException("Name, email, and password cannot be empty"))
        }
        if (password.length < 8) {
            return Result.failure(IllegalArgumentException("Password must be at least 8 characters"))
        }

        //create the Auth account.If this fails, stop and pass the error up.
        val authUser: AuthUser = authRepository.signUpWithEmail(email, password, name).getOrElse {
            return Result.failure(it)
        }

        //save the matching profile document, keyed by the auth id.

        val profile = UserProfile(
            uid = authUser.id,
            name = name,
            email = authUser.email
        )

        android.util.Log.d("SignUp", "saving profile for ${profile.uid}")
        val saveResult = userRepository.createUserProfile(profile)
        android.util.Log.d("SignUp", "save finished: $saveResult")
        return saveResult.map { authUser }

    }

}