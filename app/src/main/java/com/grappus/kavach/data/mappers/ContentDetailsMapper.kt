package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ContentDetailDto
import com.grappus.kavach.domain.model.response_model.ContentDetailData
import com.grappus.kavach.domain.model.response_model.ContentDetails

class ContentDetailsMapper : Mapper<ContentDetailDto, ContentDetails> {
    override fun fromMap(from: ContentDetailDto): ContentDetails {
        val contentDetailsData = ContentDetailData(
            id = from.data.id,
            category = from.data.category,
            contentKey = from.data.contentKey,
            contentType = from.data.contentType,
            createdAt = from.data.createdAt,
            description = from.data.description,
            isPortrait = from.data.isPortrait,
            readTime = from.data.readTime,
            thumbnail = from.data.thumbnail,
            title = from.data.title,
            updatedAt = from.data.updatedAt,
        )
        return ContentDetails(
            data = contentDetailsData
        )
    }

}