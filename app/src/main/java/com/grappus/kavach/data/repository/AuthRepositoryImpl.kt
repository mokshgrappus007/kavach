package com.grappus.kavach.data.repository

import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.mappers.OtpRequestMapper
import com.grappus.kavach.data.mappers.OtpVerifiedWrapper
import com.grappus.kavach.data.mappers.OtpVerifyRequestMapper
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.model.response_model.OtpVerified
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.utils.GenericException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val kavachApi: KavachApi) : AuthRepository {
    override suspend fun getOtp(otpRequest: OtpSentRequest): Boolean {
        try {
            val response = kavachApi.sendOtp(OtpRequestMapper().fromMap(otpRequest))
            if (response.isSuccessful) {
                return true
            } else {
                throw GenericException(message = "Unable to send otp")
            }
        } catch (e: GenericException) {
            throw e
        }
    }


    override suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): OtpVerified {
        try {
            val response = kavachApi.verifyOtp(OtpVerifyRequestMapper().fromMap(otpVerifyRequest))
            if (response.isSuccessful) {
                val authToken = response.headers()["x-auth-token"]
                if (authToken != null) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        return OtpVerifiedWrapper(authToken = authToken).fromMap(responseBody)
                    } else {
                        throw GenericException(message = "No response")
                    }
                } else {
                    throw GenericException(message = "No auth token present")
                }
            } else {
                throw GenericException(message = "Unable to verify otp")
            }
        } catch (e: GenericException) {
            throw e
        }
    }
}