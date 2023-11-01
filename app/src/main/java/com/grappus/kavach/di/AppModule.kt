package com.grappus.kavach.di

import android.content.SharedPreferences
import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.domain.data_source.SharedPrefManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(sharedPrefManager: SharedPrefManager): SharedPreferences {
        return sharedPrefManager.getSharedPref()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sharedPreferences: SharedPreferences): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val token = sharedPreferences.getString("AUTH_KEY", "") ?: ""
            val requestBuilder = original
                .newBuilder()

            if (token.isNotEmpty()) {
                requestBuilder
                    .header("Authorization", "Bearer $token")
            }

            val request = requestBuilder
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(authInterceptor: Interceptor): Retrofit {

        val logInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .build();

        val moshi =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideKavachApi(retrofit: Retrofit): KavachApi {
        return retrofit.create(KavachApi::class.java)
    }

}