package com.github.dnaka91.beatfly.model

data class LicensePair(
    val value: String,
    val url: String
)

data class SongLicense(
    val name: LicensePair,
    val artist: LicensePair,
    val platform: LicensePair,
    val cover: PictureLicense
) {
    companion object {
        fun of(song: Song) = song.run {
            SongLicense(
                name = LicensePair(title, titleUrl),
                artist = LicensePair(artist, artistUrl),
                platform = LicensePair(platform, platformUrl),
                cover = PictureLicense.of(cover)
            )
        }
    }
}

data class ModeratorLicense(
    val name: String,
    val picture: PictureLicense
) {
    companion object {
        fun of(mod: Moderator) = mod.run {
            ModeratorLicense(
                name = name,
                picture = PictureLicense.of(picture)
            )
        }
    }
}

data class PictureLicense(
    val artist: LicensePair,
    val platform: LicensePair
) {
    companion object {
        fun of(picture: Picture) = picture.run {
            PictureLicense(
                artist = LicensePair(artist, artistUrl),
                platform = LicensePair(platform, platformUrl)
            )
        }
    }
}
