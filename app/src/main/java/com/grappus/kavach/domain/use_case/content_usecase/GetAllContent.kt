package com.grappus.kavach.domain.use_case.content_usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class GetAllContent(
    private val contentRepository: ContentRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(
        contentType: String? = null,
        personalized: Boolean = false
    ): ResponseData<Content> {
        val dateSt = "2017-04-08T18:39:42Z"
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formattedDate = LocalDateTime.parse(dateSt, dateFormatter)
        val res = DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mma").format(formattedDate) //

        return when (val response = contentRepository.getContent(contentType = contentType, personalized)) {
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
                        },
                        createdAt = DateTimeFormatter.ofPattern("dd MMM ''yy").format(Instant.parse(content.createdAt).atZone(ZoneId.of("UTC")))
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