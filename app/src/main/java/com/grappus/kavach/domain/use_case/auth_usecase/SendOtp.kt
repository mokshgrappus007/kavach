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
        val phoneRegex = "^[789]\\d{9}$"

        if (!phoneNumber.matches(phoneRegex.toRegex()) || phoneNumber.isEmpty() || phoneNumber.length < 10) {
            return ResponseData.Error(message = "Invalid phone number")
        }

        val otpRequest = OtpSentRequest(phoneNumber = phoneNumber)
        return authRepository.getOtp(otpRequest = otpRequest)
    }
}