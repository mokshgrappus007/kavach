package com.grappus.kavach.domain.use_case.content_usecase

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.grappus.kavach.domain.ErrorType
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository
import com.grappus.kavach.domain.utils.GenericException
import com.grappus.kavach.domain.utils.UnauthorizedException
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
            config = PagingConfig(5, enablePlaceholders = true),
            pagingSourceFactory = { pagingSource }
        )

        return pager.flow.map { pagingData ->
            pagingData.map { content ->
                val response =
                    contentRepository.getImage(
                        content.thumbnail,
                        contentType = "thumbnail"
                    )
                content.copy(
                    thumbnail = Uri.encode(response.downloadURL),
                    createdAt = DateTimeFormatter.ofPattern("dd MMM ''yy")
                        .format(
                            Instant.parse(content.createdAt).atZone(ZoneId.of("UTC"))
                        )
                )
            }
        }
    }
}
