/*
 * Copyright (C) 2019 Dominik Nakamura
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
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
