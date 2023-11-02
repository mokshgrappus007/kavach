package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository

class GetAllContent(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(contentType: String): ResponseData<Content> {
        return contentRepository.getContent(contentType = contentType)
    }
}