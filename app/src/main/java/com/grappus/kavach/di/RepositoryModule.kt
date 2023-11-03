package com.grappus.kavach.di

import com.grappus.kavach.data.repository.AuthRepositoryImpl
import com.grappus.kavach.data.repository.ContentRepositoryImpl
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.repository.ContentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindContentRepository(
        contentRepositoryImpl: ContentRepositoryImpl
    ): ContentRepository
}