package com.grappus.kavach.di

import android.content.SharedPreferences
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.use_case.auth_usecase.AuthUseCase
import com.grappus.kavach.domain.use_case.auth_usecase.CreateNewUser
import com.grappus.kavach.domain.use_case.auth_usecase.GetOtp
import com.grappus.kavach.domain.use_case.auth_usecase.VerifyOtp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideAuthUseCase(authRepository: AuthRepository, sharedPreferences: SharedPreferences): AuthUseCase {
        return AuthUseCase(
            getOtp = GetOtp(authRepository),
            verifyOtp = VerifyOtp(authRepository, sharedPreferences),
            createNewUser = CreateNewUser(authRepository)
        )
    }
}