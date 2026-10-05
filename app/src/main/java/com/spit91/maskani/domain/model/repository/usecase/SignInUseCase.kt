package com.spit91.maskani.domain.model.repository.usecase
import javax.inject.Inject
import com.spit91.maskani.domain.model.AuthUser
import com.spit91.maskani.domain.model.UserProfile
import com.spit91.maskani.domain.model.repository.AuthRepository
import com.spit91.maskani.domain.model.repository.UserRepository

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
){
    suspend operator fun invoke(email:String, password:String): Result<AuthUser> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Email and password cannot be empty"))
        }
        //Step 1: sign in. if this fails, pass the error straight back.
        val signInResult = authRepository.signInWithEmail(email, password)
        if (signInResult.isFailure) {
            return signInResult
        }
        val authUser = signInResult.getOrThrow()

        //Step 2: Safety net.only create a profile if the read SUCCEEDED and found nothing
        val profileResult = userRepository.getUserProfile()
        if (profileResult.isSuccess && profileResult.getOrNull() == null) {
            userRepository.createUserProfile(
                UserProfile(
                    uid = authUser.id,
                    name = authUser.displayName.orEmpty(),
                    email = authUser.email
                )
            )
        }
        // sign in succeeds regardless of what happened with the profile
        return Result.success(authUser)
    }
}