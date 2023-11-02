package com.grappus.kavach.domain.use_case.auth_usecase

data class AuthUseCase(
    val getOtp: GetOtp,
    val verifyOtp: VerifyOtp,
    val createNewUser: CreateNewUser
)
