package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ContentDto
import com.grappus.kavach.domain.model.response_model.Content

class ContentMapper : Mapper<Content, ContentDto> {
    override fun fromMap(from: Content): ContentDto {
        return ContentDto(something = from.something)
    }
}