package com.grappus.kavach.data.data_source

import com.grappus.kavach.data.dto.request_body.NewUserRequestBodyDto
import com.grappus.kavach.data.dto.request_body.OtpSentRequestDto
import com.grappus.kavach.data.dto.request_body.OtpVerifyRequestDto
import com.grappus.kavach.data.dto.response_body.ContentDto
import retrofit2.http.*

interface KavachApi {
    @POST("user/otp/send")
    suspend fun sendOtp(@Body otpRequestDto: OtpSentRequestDto): String

    @POST("user/otp/verify")
    suspend fun verifyOtp(@Body otpVerifyDto: OtpVerifyRequestDto): String

    @POST("user/new")
    suspend fun createNewUser(@Body newUserRequestBodyDto: NewUserRequestBodyDto): String

    @GET("user/content/all")
    suspend fun getContent(
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 10,
        @Query("type") type: String = "TEXT"
    ): ContentDto

    @GET("user/content/{contentId}")
    suspend fun getContentDetail(
        @Path("contentId") contentId: String
    )


}
