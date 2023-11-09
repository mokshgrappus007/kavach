package com.grappus.kavach.domain.use_case.content_usecase

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class GetAllContent(
    private val contentRepository: ContentRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        contentType: String?,
    ): Flow<PagingData<Content>> {

        val pagingSource = ContentPagingSource(
            contentRepository = contentRepository,
            contentType = contentType,
        )

        val pager = Pager(
            config = PagingConfig(10, enablePlaceholders = true),
            pagingSourceFactory = { pagingSource }
        )

        return pager.flow.map { pagingData ->
            pagingData.map { content ->
                val response = contentRepository.getImage(content.thumbnail, contentType = "thumbnail")
                content.copy(
                    thumbnail = when (response) {
                        is ResponseData.Success -> {
                            Uri.encode(response.data.data.downloadURL)
                        }

                        is ResponseData.Error -> {
                            "null"
                        }
                    },
                    createdAt = DateTimeFormatter.ofPattern("dd MMM ''yy")
                        .format(Instant.parse(content.createdAt).atZone(ZoneId.of("UTC")))
                )
            }
        }
    }
}
