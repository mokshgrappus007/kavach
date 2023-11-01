package com.grappus.kavach.domain.model.request_model

data class NewUserRequestBody(
    val name: String,
    val age: String,
    val gender: String,
    val phone: String,
)
