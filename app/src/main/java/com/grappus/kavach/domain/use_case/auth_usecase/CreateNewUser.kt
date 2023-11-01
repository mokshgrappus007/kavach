package com.grappus.kavach.domain.use_case.auth_usecase

import com.grappus.kavach.domain.Response
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody
import com.grappus.kavach.domain.repository.AuthRepository

class CreateNewUser(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(newUserRequestBody: NewUserRequestBody):Response<String>{
        return authRepository.createNewUser(newUserRequestBody)
    }
}