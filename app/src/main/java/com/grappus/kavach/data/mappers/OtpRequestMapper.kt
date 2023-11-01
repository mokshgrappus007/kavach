package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.request_body.OtpSentRequestDto
import com.grappus.kavach.domain.model.request_model.OtpSentRequest

class OtpRequestMapper:Mapper<OtpSentRequest, OtpSentRequestDto> {

    override fun fromMap(from: OtpSentRequest): OtpSentRequestDto {
        return OtpSentRequestDto(
            phoneNumber = from.phoneNumber
        )
    }
}