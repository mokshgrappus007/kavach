package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.model.response_model.User
import com.grappus.kavach.domain.repository.AuthRepository

class CreateNewUser(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(newUserRequestBody: NewUserRequestBody):ResponseData<User>{
        return authRepository.createNewUser(newUserRequestBody)
    }
}