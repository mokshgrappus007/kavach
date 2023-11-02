package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.UserResponseDto
import com.grappus.kavach.domain.model.response_model.User
import com.grappus.kavach.domain.model.response_model.UserData

class UserMapper : Mapper<UserResponseDto, User> {
    override fun fromMap(from: UserResponseDto): User {
        val userData = UserData(
            id = from.data.id,
            age = from.data.age,
            bookmarks = from.data.bookmarks,
            createdAt = from.data.createdAt,
            updatedAt = from.data.updatedAt,
            feel = from.data.feel,
            gender = from.data.gender,
            interests = from.data.interests,
            isPhoneUnique = from.data.isPhoneUnique,
            liked = from.data.liked,
            name = from.data.name,
            phone = from.data.phone,
        )
        return User(data = userData)
    }
}