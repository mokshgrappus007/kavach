package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.request_body.NewUserRequestBodyDto
import com.grappus.kavach.domain.model.request_model.NewUserRequestBody

class NewUserRequestBodyMapper : Mapper<NewUserRequestBody, NewUserRequestBodyDto> {
    override fun fromMap(from: NewUserRequestBody): NewUserRequestBodyDto {
        return NewUserRequestBodyDto(
            name = from.name,
            age = from.age,
            gender = from.gender,
            phone = from.phone,
        )
    }

}
