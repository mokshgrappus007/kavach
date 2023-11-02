package com.grappus.kavach.domain.model.response_model

data class ContentDetails(
    val data: ContentDetailData
)

data class ContentDetailData(
    val id: String,
    val category: String,
    val contentKey: String,
    val contentType: String,
    val createdAt: String,
    val description: String,
    val isPortrait: Boolean,
    val readTime: String,
    val thumbnail: String,
    val title: String,
    val updatedAt: String
)