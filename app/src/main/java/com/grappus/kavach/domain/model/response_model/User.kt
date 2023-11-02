package com.grappus.kavach.domain.model.response_model

data class User(
    val data: UserData
)

data class UserData(
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