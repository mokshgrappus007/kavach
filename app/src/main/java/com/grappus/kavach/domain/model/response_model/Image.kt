package com.grappus.kavach.domain.model.response_model

data class Image(
    val data: ImageData
)

data class ImageData(
    val downloadURL: String
)
