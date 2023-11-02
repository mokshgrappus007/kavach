package com.grappus.kavach.data.dto.response_body

data class OtpVerifiedDto(
    val data: OtpVerifiedData
)
data class OtpVerifiedData(
    val message: String,
    val type: String
)