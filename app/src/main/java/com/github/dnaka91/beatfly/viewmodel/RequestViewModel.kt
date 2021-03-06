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
package com.github.dnaka91.beatfly.viewmodel

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dnaka91.beatfly.R
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class RequestViewModel @ViewModelInject constructor(
    @ActivityContext private val context: Context
) : ViewModel() {
    private val _songError = MutableLiveData<String>()
    private val _artistError = MutableLiveData<String>()

    val songError: LiveData<String>
        get() = _songError

    val artistError: LiveData<String>
        get() = _artistError

    fun submit(song: String, artist: String): Boolean {
        val errors = mutableMapOf<String, String?>(
            "song" to null,
            "artist" to null
        )
        when {
            song.isBlank() -> errors["song"] = context.getString(R.string.request_song_blank)
            artist.isBlank() -> errors["artist"] = context.getString(R.string.request_artist_blank)
        }

        _songError.postValue(errors["song"])
        _artistError.postValue(errors["artist"])

        return errors.values.all { it == null }
    }
}
