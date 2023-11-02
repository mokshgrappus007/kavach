package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.ContentDetails
import com.grappus.kavach.domain.repository.ContentRepository

class GetContentDetail(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(contentId: String): ResponseData<ContentDetails> {
        return contentRepository.getContentDetails(contentId)
    }
}