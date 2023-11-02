package com.grappus.kavach.data.dto.response_body

import com.squareup.moshi.Json

data class UserResponseDto(
    val data: UserResponseData
)

data class UserResponseData(
    @Json(name = "_id")
    val id: String,
    @Json(name = "__v")
    val v: Int,
    val age: Int,
    val bookmarks: List<Any>,
    val createdAt: String,
    val feel: List<Any>,
    val gender: String,
    val interests: List<Any>,
    val isPhoneUnique: Boolean,
    val liked: List<Any>,
    val name: String,
    val phone: String,
    val uid: String,
    val updatedAt: String
)