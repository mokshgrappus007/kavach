package com.grappus.kavach.di

import android.content.SharedPreferences
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class RetrofitInstance<T>(
    private val baseUrl: String,
    private val headers: Map<String,String>,
    private val apiClass: Class<T>
) {
    operator fun invoke(): T {
        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()

            headers.forEach { (key, value) ->
                requestBuilder.header(key, value)
            }
            val request = requestBuilder
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()

            chain.proceed(request)
        }

        val logInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logInterceptor)
            .build()

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(apiClass)
    }
}
