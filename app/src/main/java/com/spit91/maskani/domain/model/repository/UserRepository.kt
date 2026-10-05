package com.spit91.maskani.domain.model.repository

import com.spit91.maskani.domain.model.UserProfile


interface UserRepository{
    suspend fun createUserProfile(profile: UserProfile): Result<Unit>
    suspend fun getUserProfile(): Result<UserProfile?>
    suspend fun updateUserProfile(profile: UserProfile): Result<Unit>

}