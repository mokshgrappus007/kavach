package com.grappus.kavach.data.repository

import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.mappers.NewUserRequestBodyMapper
import com.grappus.kavach.data.mappers.OtpRequestMapper
import com.grappus.kavach.data.mappers.OtpVerifyRequestMapper
import com.grappus.kavach.domain.Response
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val kavachApi: KavachApi) : AuthRepository {
    override suspend fun getOtp(otpRequest: OtpSentRequest): Response<String> {
        return try {
            Response.Success(
                data = kavachApi.sendOtp(OtpRequestMapper().fromMap(otpRequest))
            )
        } catch (e: Exception) {
            Response.Error(
                message = e.message ?: "Unable to verify mobile number"
            )
        }
    }

    override suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): Response<String> {
        return try {
            Response.Success(
                data = kavachApi.verifyOtp(OtpVerifyRequestMapper().fromMap(otpVerifyRequest))
            )
        } catch (e: Exception) {
            Response.Error(
                message = e.message ?: "Unable to verify OTP"
            )
        }
    }

    override suspend fun createNewUser(newUserRequestBody: NewUserRequestBody): Response<String> {
        return try {
            Response.Success(
                data = kavachApi.createNewUser(NewUserRequestBodyMapper().fromMap(newUserRequestBody))
            )
        } catch (e: Exception) {
            Response.Error(
                message = e.message ?: "Unable to verify OTP"
            )
        }
    }
}