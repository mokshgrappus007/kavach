package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository

class GetAllContent(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke(
        contentType: String? = null,
        personalized: Boolean = false
    ): ResponseData<Content> {
        val response = contentRepository.getContent(contentType = contentType, personalized)
        return when (response) {
            is ResponseData.Success -> {
                val updatedContents = response.data.data.content.map { content ->
                    val imageUrlResponse =
                        contentRepository.getImage(
                            fileName = content.thumbnail,
                            contentType = "thumbnail"
                        )
                    content.copy(
                        thumbnail = when (imageUrlResponse) {
                            is ResponseData.Success -> {
                                imageUrlResponse.data.data.downloadURL
                            }

                            is ResponseData.Error -> {
                                ""
                            }
                        }
                    )
                }
                val updatedResponse =
                    response.data.copy(data = response.data.data.copy(content = updatedContents))
                ResponseData.Success(data = updatedResponse)
            }

            is ResponseData.Error -> {
                ResponseData.Error(message = response.message)
            }
        }
    }
}