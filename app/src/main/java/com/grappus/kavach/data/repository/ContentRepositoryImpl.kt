package com.grappus.kavach.data.repository

import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.dto.request_body.ImageRequestBodyDto
import com.grappus.kavach.data.mappers.ContentDetailsMapper
import com.grappus.kavach.data.mappers.ContentMapper
import com.grappus.kavach.data.mappers.ImageMapper
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.ContentDetails
import com.grappus.kavach.domain.model.response_model.Image
import com.grappus.kavach.domain.repository.ContentRepository
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val kavachApi: KavachApi) : ContentRepository {
    override suspend fun getContent(contentType: String): ResponseData<Content> {
        return try {
            val response = kavachApi.getContent(type = contentType)
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ResponseData.Success(
                        data = ContentMapper().fromMap(successResult)
                    )
                } else {
                    ResponseData.Error(
                        message = "No content"
                    )
                }
            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to get content details")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }

    override suspend fun getContentDetails(contentId: String): ResponseData<ContentDetails> {
        return try {
            val response = kavachApi.getContentDetail(contentId = contentId)
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ResponseData.Success(
                        data = ContentDetailsMapper().fromMap(successResult)
                    )
                } else {
                    ResponseData.Error(
                        message = "No content Detail"
                    )
                }
            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to get content details")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }

    override suspend fun getImage(fileName: String, contentType: String): ResponseData<Image> {
        return try {
            val imageRequestBodyDto = ImageRequestBodyDto(fileName = fileName,contentType = contentType)
            val response = kavachApi.getContentImage(imageRequestBodyDto = imageRequestBodyDto)
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ResponseData.Success(
                        data = ImageMapper().fromMap(successResult)
                    )
                } else {
                    ResponseData.Error(
                        message = "Image not found"
                    )
                }
            } else {
                val errorResponse = response.errorBody()
                ResponseData.Error(message = "Unable to get image details")
            }
        } catch (e: Exception) {
            ResponseData.Error(message = "Unable to proceed request")
        }
    }
}