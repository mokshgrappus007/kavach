package com.grappus.kavach.di

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
    fun provideRetrofitInstance(): Retrofit {

        val interceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original
                .newBuilder()
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

}