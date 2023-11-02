package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.OtpVerifiedDto
import com.grappus.kavach.domain.model.response_model.OtpVerified

class OtpVerifiedWrapper(private val authToken: String) : Mapper<OtpVerifiedDto, OtpVerified> {
    override fun fromMap(from: OtpVerifiedDto): OtpVerified {
        return OtpVerified(
            isExistingUser = from.data.existingUser.isNotEmpty(),
            authToken = authToken
        )
    }
}