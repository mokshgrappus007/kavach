package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ContentDto
import com.grappus.kavach.domain.model.response_model.Content

class ContentMapper : Mapper<ContentDto, List<Content>> {
    override fun fromMap(from: ContentDto): List<Content> {
        return from.data.content.map {
            Content(
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
        }
    }
}