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
package com.github.dnaka91.beatfly.service

import androidx.lifecycle.LiveData
import com.github.dnaka91.beatfly.model.*

interface RadioService {
    fun currentSong(): LiveData<Song>
    fun loadHistory(): LiveData<List<Song>>
    fun loadModerators(): LiveData<List<Moderator>>

    fun songLicenses(): List<SongLicense>
    fun moderatorLicenses(): List<ModeratorLicense>

    fun login(username: String, password: String): LoginResponse
}
