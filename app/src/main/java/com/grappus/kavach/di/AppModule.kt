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
    fun provideRetrofitInstance(authInterceptor: Interceptor): KavachApi {

        val logInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .build()

        val moshi =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(KavachApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTwitchApis(sharedPreferences: SharedPreferences): TwitchApi {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val token = sharedPreferences.getString("TWITCH_ACCESS", "") ?: ""
            val requestBuilder = original
                .newBuilder()

            if (token.isNotEmpty()) {
                requestBuilder
                    .header("Authorization", "Bearer $token")
            }
            val request = requestBuilder
                .header("Client-Id", "bktdr8rtppds8dk55fggg8yqezcgqu")
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()


            return@Interceptor chain.proceed(request)
        }

        val logInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(logInterceptor)
            .build()

        val moshi =
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

        return Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TwitchApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEthereum(@ApplicationContext context: Context): Ethereum {
        return Ethereum(context)
    }
}
