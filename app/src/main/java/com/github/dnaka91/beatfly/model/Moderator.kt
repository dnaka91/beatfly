package com.github.dnaka91.beatfly.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Moderator(
    val name: String,
    val rating: Double,
    val rateCount: Int,
    val picture: Picture
)