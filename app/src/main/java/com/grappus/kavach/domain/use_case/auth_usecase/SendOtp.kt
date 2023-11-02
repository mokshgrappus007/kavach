package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.repository.AuthRepository
import java.util.regex.Pattern

typealias isOtpSent = Boolean

class SendOtp(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): ResponseData<isOtpSent> {
        val phoneRegex = "^[0-9]{10}\$" // Example: 10 digits

        if (!phoneNumber.matches(phoneRegex.toRegex())) {
            return ResponseData.Error(message = "Invalid phone number")
        }

        val otpRequest = OtpSentRequest(phoneNumber = phoneNumber)
        return authRepository.getOtp(otpRequest = otpRequest)
    }
}