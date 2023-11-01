package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.Response
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest
import com.grappus.kavach.domain.repository.AuthRepository

class VerifyOtp(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String, otp: String): Response<String> {
        val otpVerifyRequest = OtpVerifyRequest(phoneNumber = phoneNumber,otp = otp)
        return authRepository.verifyOtp(otpVerifyRequest)
    }
}
