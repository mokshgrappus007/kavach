package com.grappus.kavach.domain.use_case.auth_usecase

import android.content.SharedPreferences
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.utils.GenericException

typealias isExistingUser = Boolean

class VerifyOtp(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
) {
    suspend operator fun invoke(phoneNumber: String, otp: String): ResponseData<isExistingUser> {
        val otpVerifyRequest = OtpVerifyRequest(phoneNumber = phoneNumber, otp = otp)
        return try {
            val authResponse = authRepository.verifyOtp(otpVerifyRequest)
            val authToken = authResponse.authToken
            sharedPreferences.edit().putString("AUTH_KEY", authToken).apply()
            ResponseData.Success(data = authResponse.isExistingUser)
        } catch (e: GenericException) {
            ResponseData.Error(errorType = ErrorType.Generic(message = e.message.toString()))
        }

    }
}
