package com.grappus.kavach.di

import android.content.SharedPreferences
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.repository.ContentRepository
import com.grappus.kavach.domain.use_case.auth_usecase.*
import com.grappus.kavach.domain.use_case.content_usecase.ContentUseCase
import com.grappus.kavach.domain.use_case.content_usecase.GetAllContent
import com.grappus.kavach.domain.use_case.content_usecase.GetImageUrl
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
            sendOtp = SendOtp(authRepository),
            verifyOtp = VerifyOtp(authRepository, sharedPreferences),
        )
    }

    @Provides
    @Singleton
    fun provideContentUseCase(contentRepository: ContentRepository):ContentUseCase{
        return ContentUseCase(
            getAllContent = GetAllContent(contentRepository),
            getImage = GetImageUrl(contentRepository)
        )
    }
}