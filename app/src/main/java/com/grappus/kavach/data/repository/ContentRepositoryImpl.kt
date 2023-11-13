package com.grappus.kavach.data.repository

import com.grappus.kavach.data.data_source.KavachApi
import com.grappus.kavach.data.dto.request_body.ImageRequestBodyDto
import com.grappus.kavach.data.mappers.ContentMapper
import com.grappus.kavach.data.mappers.ImageMapper
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.Image
import com.grappus.kavach.domain.repository.ContentRepository
import com.grappus.kavach.domain.utils.GenericException
import com.grappus.kavach.domain.utils.UnauthorizedException
import okhttp3.internal.http.HTTP_UNAUTHORIZED
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(private val kavachApi: KavachApi) :
    ContentRepository {
    override suspend fun getContent(
        page: Int,
        pageSize: Int,
        contentType: String?,
    ): List<Content> {
        try {
            val response =
                kavachApi.getContent(
                    type = contentType,
                    page = page,
                    size = pageSize,
                )
            if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    return ContentMapper().fromMap(successResult)
                } else {
                    throw GenericException(message = "No Content")
                }
            } else if (response.code() == HTTP_UNAUTHORIZED) {
                throw UnauthorizedException(message = "token expired")
            } else {
                throw GenericException(message = "Something went wrong")
            }
        } catch (e: GenericException) {
            throw e
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: Exception) {
            throw GenericException(message = e.message.toString())
        }
    }

    override suspend fun getImage(fileName: String, contentType: String): Image {
        try {
            val imageRequestBodyDto =
                ImageRequestBodyDto(fileName = fileName, contentType = contentType)
            val response = kavachApi.getContentImage(imageRequestBodyDto = imageRequestBodyDto)
            return if (response.isSuccessful) {
                val successResult = response.body()
                if (successResult != null) {
                    ImageMapper().fromMap(successResult)
                } else {
                    throw GenericException(message = "Image not found")
                }
            } else if (response.code() == HTTP_UNAUTHORIZED) {
                throw UnauthorizedException(message = "token expired")
            } else {
                Image(
                    downloadURL = "null"
                )
            }
        } catch (e: GenericException) {
            throw e
        } catch (e: UnauthorizedException) {
            throw e
        }
    }
}