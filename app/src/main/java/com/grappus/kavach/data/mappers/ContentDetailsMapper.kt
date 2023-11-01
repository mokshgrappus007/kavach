package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ContentDetailDto
import com.grappus.kavach.domain.model.response_model.ContentDetails

class ContentDetailsMapper : Mapper<ContentDetails, ContentDetailDto> {
    override fun fromMap(from: ContentDetails): ContentDetailDto {
        return ContentDetailDto(someData = from.something)
    }
}