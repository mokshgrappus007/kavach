package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.repository.ContentRepository
import com.grappus.kavach.domain.utils.GenericException
import com.grappus.kavach.domain.utils.UnauthorizedException

typealias ImageUrl = String

class GetImageUrl(
    private val repository: ContentRepository
) {
    suspend operator fun invoke(fileName: String, contentType: String): ResponseData<ImageUrl> {
        return try {
            val responseData = repository.getImage(fileName = fileName, contentType = contentType)
            ResponseData.Success(data = responseData.downloadURL)
        } catch (e: GenericException) {
            ResponseData.Error(errorType = ErrorType.Generic(message = e.message.toString()))
        } catch (e: UnauthorizedException) {
            ResponseData.Error(errorType = ErrorType.Unauthorized(message = "Please login again"))
        }
    }
}