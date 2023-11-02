package com.grappus.kavach.data.dto.response_body

data class ImageDto(
    val data: ImageDataDto
)

data class ImageDataDto(
    val downloadURL: String
)