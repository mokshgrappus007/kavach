package com.grappus.kavach.data.dto.request_body

import com.squareup.moshi.Json

data class OtpSentRequestDto(
    @Json(name = "phone")
    val phoneNumber: String
)