package com.grappus.kavach.data.repository

import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.mappers.NewUserRequestBodyMapper
import com.grappus.kavach.data.mappers.OtpRequestMapper
import com.grappus.kavach.data.mappers.OtpVerifiedWrapper
import com.grappus.kavach.data.mappers.OtpVerifyRequestMapper
import com.grappus.kavach.data.mappers.UserMapper
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.model.response_model.OtpVerified
import com.grappus.kavach.domain.model.response_model.User
import com.grappus.kavach.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val kavachApi: KavachApi) : AuthRepository {
    override suspend fun getOtp(otpRequest: OtpSentRequest): ResponseData<Boolean> {
        return try {
            val response = kavachApi.sendOtp(OtpRequestMapper().fromMap(otpRequest))
            if (response.isSuccessful) {
                val successResult = response.body()
                ResponseData.Success(
                    data = true
                )
            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to send otp")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }


    override suspend fun verifyOtp(otpVerifyRequest: OtpVerifyRequest): ResponseData<OtpVerified> {
        return try {
            val response = kavachApi.verifyOtp(OtpVerifyRequestMapper().fromMap(otpVerifyRequest))
            if (response.isSuccessful) {
                val authToken = response.headers()["x-auth-token"]
                if (authToken != null) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        ResponseData.Success(
                            data = OtpVerifiedWrapper(authToken = authToken).fromMap(
                                responseBody
                            )
                        )
                    } else {
                        ResponseData.Error(message = "No Response")
                    }
                } else {
                    ResponseData.Error(message = "No auth token present")
                }
            } else {
                val errorResponse = response.errorBody()
                val errorMessage = errorResponse?.string() ?: "Unable to verify otp"
                ResponseData.Error(message = errorMessage)
            }
        } catch (e: Exception) {
            ResponseData.Error(message = e.message ?: "Unable to proceed request")
        }
    }

    override suspend fun createNewUser(newUserRequestBody: NewUserRequestBody): ResponseData<User> {
        return try {
            val response =
                kavachApi.createNewUser(NewUserRequestBodyMapper().fromMap(newUserRequestBody))
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ResponseData.Success(
                        data = UserMapper().fromMap(successResult)
                    )
                } else {
                    ResponseData.Error(
                        message = "User data is null"
                    )
                }

            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to create user")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }

    override suspend fun getCurrentUser(): ResponseData<User> {
        return try {
            val response = kavachApi.getCurrentUser()
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ResponseData.Success(
                        data = UserMapper().fromMap(successResult)
                    )
                } else {
                    ResponseData.Error(
                        message = "User data is null"
                    )
                }
            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to create user")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }
}