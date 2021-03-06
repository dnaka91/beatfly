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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dnaka91.beatfly.model.LoginResponse
import com.github.dnaka91.beatfly.model.Moderator
import com.github.dnaka91.beatfly.model.ModeratorLicense
import com.github.dnaka91.beatfly.model.Review
import com.github.dnaka91.beatfly.model.Song
import com.github.dnaka91.beatfly.model.SongLicense
import com.github.dnaka91.beatfly.model.User
import com.squareup.moshi.JsonAdapter
import dagger.hilt.android.qualifiers.ApplicationContext
import okio.buffer
import okio.source
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRadioService @Inject constructor(
    @ApplicationContext context: Context,
    localBroadcastManager: LocalBroadcastManager,
    songAdapter: JsonAdapter<List<Song>>,
    modAdapter: JsonAdapter<List<Moderator>>,
    reviewAdapter: JsonAdapter<List<Review>>,
    userAdapter: JsonAdapter<List<User>>
) : RadioService {
    private val current = MutableLiveData<Song>()
    private val history = MutableLiveData<List<Song>>()
    private val moderators = MutableLiveData<List<Moderator>>()

    private val songs = context.loadJson(METADATA_FILE, songAdapter)
    private val songLicenses = songs.map { SongLicense.of(it) }
    private val mods = context.loadJson(MODERATORS_FILE, modAdapter)
    private val modLicenses = mods.map { ModeratorLicense.of(it) }
    private val reviews = context.loadJson(REVIEWS_FILE, reviewAdapter)
    private val users = context.loadJson(USERS_FILE, userAdapter)

    private val historySongs: MutableList<Song>
    private var currentSong: Song

    init {
        historySongs = songs.shuffled().take(2).toMutableList()
        currentSong = songs.first()

        history.postValue(historySongs)
        moderators.postValue(mods)
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val track = intent?.getStringExtra(PlayerService.EXTRA_TRACK_CHANGED_TRACK_ID) ?: ""
            if (track.isNotEmpty()) {
                val song = songs.find { it.id == track } ?: return

                while (historySongs.size >= HISTORY_LIMIT) {
                    historySongs.removeAt(0)
                }

                historySongs += currentSong
                currentSong = song

                history.postValue(historySongs)
                current.postValue(currentSong)
            }
        }
    }

    init {
        localBroadcastManager.registerReceiver(
            receiver,
            IntentFilter(PlayerService.BROADCAST_TRACK_CHANGED)
        )
    }

    override fun currentSong(): LiveData<Song> = current

    override fun loadHistory(): LiveData<List<Song>> = history

    override fun loadModerators(): LiveData<List<Moderator>> = moderators

    override fun loadReviews(modId: String): LiveData<List<Review>> =
        reviews.filter { it.modId == modId }
            .let(::MutableLiveData)

    override fun songLicenses(): List<SongLicense> = songLicenses

    override fun moderatorLicenses(): List<ModeratorLicense> = modLicenses

    override fun login(username: String, password: String): LoginResponse =
        users.find { it.username == username && it.password == password }
            ?.let { LoginResponse.Success(it.username, it.moderator) }
            ?: LoginResponse.Error

    companion object {
        private const val METADATA_FILE = "metadata.json"
        private const val MODERATORS_FILE = "moderators.json"
        private const val REVIEWS_FILE = "reviews.json"
        private const val USERS_FILE = "users.json"
        private const val HISTORY_LIMIT = 100

        private fun <T> Context.loadJson(file: String, adapter: JsonAdapter<List<T>>) =
            assets.open(file)
                .source()
                .buffer()
                .use(adapter::fromJson)
                .orEmpty()
    }
}
