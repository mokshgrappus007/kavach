package com.grappus.kavach.domain.use_case.content_usecase

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ContentPagingSource(
    private val contentRepository: ContentRepository,
    private val contentType: String? = null,
) : PagingSource<Int, Content>() {
    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
        val page = params.key ?: 1
        return try {
            val response = contentRepository.getContent(
                page = page,
                contentType = contentType,
            )
            when (response) {
                is ResponseData.Success -> {
                    LoadResult.Page(
                        data = response.data,
                        prevKey = null,
                        nextKey = if (response.data.isEmpty()) null else page.plus(1)
                    )
                }

                is ResponseData.Error -> {
                    throw Exception(response.message)
                }
            }
        } catch (e: Exception) {
            Log.v("exception in paging source", e.message.toString())
            LoadResult.Error(
                e
            )
        }
    }

}