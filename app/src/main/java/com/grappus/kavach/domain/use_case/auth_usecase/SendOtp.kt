package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.OtpSentRequest
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.utils.GenericException
import java.util.regex.Pattern

typealias isOtpSent = Boolean

class SendOtp(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(phoneNumber: String): ResponseData<isOtpSent> {
        return try {
            val phoneRegex = "^[789]\\d{9}$"

            if (!phoneNumber.matches(phoneRegex.toRegex()) || phoneNumber.isEmpty() || phoneNumber.length < 10) {
                throw GenericException(message = "Invalid phone number")
            }

            val response =
                authRepository.getOtp(otpRequest = OtpSentRequest(phoneNumber = phoneNumber))

            ResponseData.Success(data = response)
        } catch (e: GenericException) {
            ResponseData.Error(errorType = ErrorType.Generic(message = e.message.toString()))
        }
    }
}