package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.User
import com.grappus.kavach.domain.repository.AuthRepository

class GetCurrentUserDetails(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke():ResponseData<User>{
       return authRepository.getCurrentUser()
    }
}