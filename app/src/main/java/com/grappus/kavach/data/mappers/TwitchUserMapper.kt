package com.grappus.kavach.data.mappers

import com.grappus.kavach.data.dto.response_body.TwitchUserDto
import com.grappus.kavach.domain.model.response_model.TwitchUser

class TwitchUserMapper:Mapper<TwitchUserDto,TwitchUser> {
    override fun fromMap(from: TwitchUserDto): TwitchUser {
        return TwitchUser(
            displayName = from.twitchData[0].displayName
        )
    }
}