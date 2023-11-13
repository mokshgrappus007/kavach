package com.grappus.kavach.domain.repository

import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.Image

interface ContentRepository {
    suspend fun getContent(
        page: Int,
        pageSize: Int,
        contentType: String?,
    ): List<Content>

    suspend fun getImage(fileName: String, contentType: String): Image
}
