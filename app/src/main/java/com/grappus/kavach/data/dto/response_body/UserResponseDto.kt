package com.grappus.kavach.data.dto.response_body

import com.squareup.moshi.Json

data class UserResponseDto(
    val data: UserResponseData
)

data class UserResponseData(
    @Json(name = "_id")
    val id: String,
    val age: Int? = null,
    val bookmarks: List<Any>? = null,
    val createdAt: String? = null,
    val feel: List<Any>? = null,
    val gender: String? = null,
    val interests: List<Any>? = null,
    val isPhoneUnique: Boolean? = null,
    val liked: List<Any>? = null,
    val name: String,
    val phone: String,
    val updatedAt: String? = null
)