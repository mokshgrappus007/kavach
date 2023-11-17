package com.grappus.kavach.domain.use_case.auth_usecase

data class AuthUseCase(
    val sendOtp: SendOtp,
    val verifyOtp: VerifyOtp,
    val getTwitchUser: GetTwitchUser,
)
