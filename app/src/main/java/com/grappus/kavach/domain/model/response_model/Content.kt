package com.grappus.kavach.domain.model.response_model

data class Content(
    val data: ContentData
)

data class ContentData(
    val content: List<ContentListData>,
    val count: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
    val totalPages: Int
)

data class ContentListData(
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