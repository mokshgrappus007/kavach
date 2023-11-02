package com.grappus.kavach.domain.repository

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.ContentDetails
import com.grappus.kavach.domain.model.response_model.Image

interface ContentRepository {
    suspend fun getContent(contentType: String): ResponseData<Content>

    suspend fun getContentDetails(contentId: String): ResponseData<ContentDetails>

    suspend fun getImage(fileName: String, contentType: String): ResponseData<Image>
}
