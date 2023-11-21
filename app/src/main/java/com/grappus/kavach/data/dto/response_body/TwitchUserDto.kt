package com.grappus.kavach.data.dto.response_body

import com.squareup.moshi.Json

data class TwitchUserDto(
    @Json(name = "data")
    val twitchData: List<TwitchDataDto>
)

data class TwitchDataDto(
    @Json(name = "broadcaster_type")
    val broadcasterType: String,
    @Json(name = "created_at")
    val createdAt: String,
    val description: String,
    @Json(name = "display_name")
    val displayName: String,
    val id: String,
    val login: String,
    @Json(name = "offline_image_url")
    val offlineImageUrl: String,
    @Json(name = "profile_image_url")
    val profileImageUrl: String,
    val type: String,
    @Json(name = "view_count")
    val viewCount: Int
)