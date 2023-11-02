package com.grappus.kavach.domain.use_case.auth_usecase

import android.content.SharedPreferences
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.repository.AuthRepository

class VerifyOtp(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
) {
    suspend operator fun invoke(phoneNumber: String, otp: String): ResponseData<Boolean> {
        val otpVerifyRequest = OtpVerifyRequest(phoneNumber = phoneNumber, otp = otp)
        return when (val authResponse = authRepository.verifyOtp(otpVerifyRequest)) {
            is ResponseData.Success -> {
                val authToken = authResponse.data
                sharedPreferences.edit().putString("AUTH_KEY", authToken).apply()

                ResponseData.Success(data = true)
            }

            is ResponseData.Error -> {
                ResponseData.Error(message = authResponse.message ?: "Unable to get Auth Key")
            }
        }
    }

}
