package com.grappus.kavach.domain.use_case.content_usecase

import com.grappus.kavach.domain.ResponseData
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.Image
import com.grappus.kavach.domain.repository.ContentRepository

data class ContentUseCase(
    val getAllContent: GetAllContent,
    val getContentDetail: GetContentDetail,
    val getImage: GetImageUrl
)