package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ImageDto
import com.grappus.kavach.domain.model.response_model.Image

class ImageMapper : Mapper<ImageDto, Image> {
    override fun fromMap(from: ImageDto): Image {
        return Image(
            downloadURL = from.data.downloadURL
        )
    }
}