package com.grappus.kavach.data.data_source

import com.grappus.kavach.data.dto.response_body.TwitchUserDto
import retrofit2.Response
import retrofit2.http.GET

interface TwitchApi {
    @GET("users")
    suspend fun getUser():Response<TwitchUserDto>
}