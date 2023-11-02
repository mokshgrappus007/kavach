package com.grappus.kavach.data.dto.response_body

import com.squareup.moshi.Json

data class ContentDto(
    val data: ContentDataDto
)

data class ContentDataDto(
    val content: List<ContentListDataDto>,
    val count: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val totalPages: Int
)

data class ContentListDataDto(
    @Json(name = "_id")
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