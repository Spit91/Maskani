package com.spit91.maskani.di

import com.google.firebase.auth.FirebaseAuth
import com.spit91.maskani.data.repository.FirebaseAuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.spit91.maskani.domain.model.repository.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    // 1. BINDS: Links our domain interface contract to its concrete data implementation
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: FirebaseAuthRepositoryImpl
    ): AuthRepository

    companion object {
        // 2. PROVIDES: Tells Hilt how to instantiate external SDK clients like Firebase
        @Provides
        @Singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}