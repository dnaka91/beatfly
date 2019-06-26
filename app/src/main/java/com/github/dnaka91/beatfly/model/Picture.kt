package com.github.dnaka91.beatfly.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Picture(
    val url: String,
    val artist: String,
    val platform: String,
    val artistUrl: String,
    val platformUrl: String
)