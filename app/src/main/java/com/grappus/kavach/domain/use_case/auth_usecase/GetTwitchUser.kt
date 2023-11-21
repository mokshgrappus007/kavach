package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.repository.AuthRepository
import com.grappus.kavach.domain.utils.GenericException

class GetTwitchUser(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): ResponseData<String> {
        return try {
            ResponseData.Success(data = authRepository.getTwitchUserName())
        } catch (e: GenericException) {
            ResponseData.Error(errorType = ErrorType.Generic(message = e.message.toString()))
        }
    }
}