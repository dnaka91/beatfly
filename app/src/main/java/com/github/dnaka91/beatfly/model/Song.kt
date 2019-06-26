package com.github.dnaka91.beatfly.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Song(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long,
    val platform: String,
    val titleUrl: String,
    val artistUrl: String,
    val platformUrl: String,
    val cover: Picture
)

