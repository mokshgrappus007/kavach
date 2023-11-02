package com.grappus.kavach.data.dto.response_body

data class OtpVerifiedDto(
    val data: OtpVerifiedDataDto
)

data class OtpVerifiedDataDto(
    val message: String,
    val type: String,
    val existingUser: String,
)