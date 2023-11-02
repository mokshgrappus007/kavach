package com.grappus.kavach.data.dto.request_body

data class NewUserRequestBodyDto(
    val name: String,
    val age: Int,
    val gender: String,
    val phone: String,
)
