package com.grappus.kavach.domain.model.response_model

data class User(
    val data: UserData
)

data class UserData(
    val id: String,
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