package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ContentDto
import com.grappus.kavach.domain.model.response_model.Content
import com.grappus.kavach.domain.model.response_model.ContentData
import com.grappus.kavach.domain.model.response_model.ContentListData

class ContentMapper : Mapper<ContentDto, Content> {
    override fun fromMap(from: ContentDto): Content {
        val contentData = ContentData(
            content = from.data.content.map {
                ContentListData(
                    id = it.id,
                    category = it.category,
                    contentKey = it.contentKey,
                    createdAt = it.createdAt,
                    description = it.description,
                    isPortrait = it.isPortrait,
                    readTime = it.readTime,
                    thumbnail = it.thumbnail,
                    title = it.title,
                    updatedAt = it.updatedAt,
                    contentType = it.contentType,
                )
            },
            count = from.data.count,
            hasNext = from.data.hasNext,
            hasPrevious = from.data.hasPrevious,
            totalPages = from.data.totalPages,
        )
        return Content(data = contentData)
    }
}