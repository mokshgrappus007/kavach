package com.grappus.kavach.domain.use_case.content_usecase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.repository.ContentRepository
import com.grappus.kavach.domain.utils.GenericException
import com.grappus.kavach.domain.utils.UnauthorizedException

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
        val pageSize = params.loadSize ?: 5
        return try {

            val response = contentRepository.getContent(
                page = page,
                pageSize = pageSize,
                contentType = contentType,
            )

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else page.plus(1)
            )

        } catch (e: GenericException) {
            LoadResult.Error(e)
        } catch (e: UnauthorizedException) {
            LoadResult.Error(e)
        }
    }
}