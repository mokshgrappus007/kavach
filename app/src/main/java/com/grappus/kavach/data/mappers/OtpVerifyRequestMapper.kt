package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.request_body.OtpVerifyRequestDto
import com.grappus.kavach.domain.model.request_model.OtpVerifyRequest

class OtpVerifyRequestMapper:Mapper<OtpVerifyRequest, OtpVerifyRequestDto> {
    override fun fromMap(from: OtpVerifyRequest): OtpVerifyRequestDto {
        return OtpVerifyRequestDto(otp = from.otp, phoneNumber = from.phoneNumber)
    }

}