package com.grappus.kavach.di

import android.content.Context
import android.content.SharedPreferences
import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.data_source.TwitchApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.metamask.androidsdk.Ethereum
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
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
    fun provideKavachApiInstance(sharedPreferences: SharedPreferences): KavachApi {
        val token = sharedPreferences.getString("AUTH_KEY", "") ?: ""

        val headers = mapOf(
            Pair("Authorization", "Bearer $token"),
        )
        val retrofitInstance = RetrofitInstance(
            BASE_URL,
            headers,
            KavachApi::class.java
        )
        return retrofitInstance()
    }

    @Provides
    @Singleton
    fun provideTwitchApiInstance(sharedPreferences: SharedPreferences): TwitchApi {
        val token = sharedPreferences.getString("TWITCH_ACCESS", "") ?: ""

        val headers = mapOf(
            Pair("Authorization", "Bearer $token"),
            Pair("Client-Id", "bktdr8rtppds8dk55fggg8yqezcgqu"),
        )
        val retrofitInstance = RetrofitInstance(
            "https://api.twitch.tv/helix/",
            headers,
            TwitchApi::class.java
        )
        return retrofitInstance()
    }

    @Provides
    @Singleton
    fun provideEthereum(@ApplicationContext context: Context): Ethereum {
        return Ethereum(context)
    }
}
