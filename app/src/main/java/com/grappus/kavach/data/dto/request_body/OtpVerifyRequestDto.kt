package com.grappus.kavach.data.dto.request_body

import com.squareup.moshi.Json

data class OtpVerifyRequestDto(
    @Json(name = "otp")
    val otp: String,

    @Json(name = "phone")
    val phoneNumber: String,
)
