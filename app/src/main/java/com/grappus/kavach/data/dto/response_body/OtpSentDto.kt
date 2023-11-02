package com.grappus.kavach.data.dto.response_body

import com.squareup.moshi.Json

data class OtpSentDto(
    val data: OtpSentData
)
data class OtpSentData(
    @Json(name = "request_id")
    val requestId: String,
    val type: String
)