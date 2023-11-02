package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.ImageDto
import com.grappus.kavach.domain.model.response_model.Image
import com.grappus.kavach.domain.model.response_model.ImageData

class ImageMapper : Mapper<ImageDto, Image> {
    override fun fromMap(from: ImageDto): Image {
        return Image(
            data = ImageData(
                downloadURL = from.data.downloadURL
            )
        )
    }
}