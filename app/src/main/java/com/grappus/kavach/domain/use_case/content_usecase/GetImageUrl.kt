package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.repository.ContentRepository

typealias ImageUrl = String

class GetImageUrl(
    private val repository: ContentRepository
) {
    suspend operator fun invoke(fileName: String, contentType: String): ResponseData<ImageUrl> {
        return when (val responseData = repository.getImage(fileName = fileName, contentType = contentType)) {
            is ResponseData.Success -> {
                ResponseData.Success(data = responseData.data.data.downloadURL)
            }

            is ResponseData.Error -> {
                ResponseData.Error(message = responseData.message)
            }
        }
    }
}