package com.grappus.kavach.data.data_source

import com.grappus.kavach.data.dto.request_body.ImageRequestBodyDto
import com.grappus.kavach.data.dto.request_body.NewUserRequestBodyDto
import com.grappus.kavach.data.dto.request_body.OtpSentRequestDto
import com.grappus.kavach.data.dto.request_body.OtpVerifyRequestDto
import com.grappus.kavach.data.dto.response_body.*
import retrofit2.Response
import retrofit2.http.*

interface KavachApi {
    @POST("user/otp/send")
    suspend fun sendOtp(@Body otpRequestDto: OtpSentRequestDto): Response<OtpSentDto>

    @POST("user/otp/verify")
    suspend fun verifyOtp(@Body otpVerifyDto: OtpVerifyRequestDto): Response<OtpVerifiedDto>

    @POST("user/new")
    suspend fun createNewUser(@Body newUserRequestBodyDto: NewUserRequestBodyDto): Response<UserResponseDto>
    @GET("user/current")
    suspend fun getCurrentUser(): Response<UserResponseDto>

    @GET("user/content/all")
    suspend fun getContent(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("type") type: String ?= null,
        @Query("personalized") personalized : Boolean ?= null
    ): Response<ContentDto>

    @GET("user/content/{contentId}")
    suspend fun getContentDetail(
        @Path("contentId") contentId: String
    ):Response<ContentDetailDto>

    @POST("user/content/get-download-url")
    suspend fun getContentImage(
        @Body imageRequestBodyDto: ImageRequestBodyDto
    ):Response<ImageDto>
}
