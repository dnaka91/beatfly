package com.github.dnaka91.beatfly.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dnaka91.beatfly.extension.buffered
import com.github.dnaka91.beatfly.extension.toSource
import com.github.dnaka91.beatfly.model.Moderator
import com.github.dnaka91.beatfly.model.ModeratorLicense
import com.github.dnaka91.beatfly.model.Song
import com.github.dnaka91.beatfly.model.SongLicense
import com.squareup.moshi.JsonAdapter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRadioService @Inject constructor(
    context: Context,
    songAdapter: JsonAdapter<List<Song>>,
    modAdapter: JsonAdapter<List<Moderator>>,
    localBroadcastManager: LocalBroadcastManager
) : RadioService {
    private val current = MutableLiveData<Song>()
    private val history = MutableLiveData<List<Song>>()
    private val moderators = MutableLiveData<List<Moderator>>()

    private val songs = context.loadJson(METADATA_FILE, songAdapter)
    private val songLicenses = songs.map { SongLicense.of(it) }
    private val mods = context.loadJson(MODERATORS_FILE, modAdapter)
    private val modLicenses = mods.map { ModeratorLicense.of(it) }

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
        localBroadcastManager.registerReceiver(receiver, IntentFilter(PlayerService.BROADCAST_TRACK_CHANGED))
    }

    override fun currentSong(): LiveData<Song> = current

    override fun loadHistory(): LiveData<List<Song>> = history

    override fun loadModerators(): LiveData<List<Moderator>> = moderators

    override fun songLicenses(): List<SongLicense> = songLicenses

    override fun moderatorLicenses(): List<ModeratorLicense> = modLicenses

    companion object {
        private const val METADATA_FILE = "metadata.json"
        private const val MODERATORS_FILE = "moderators.json"
        private const val HISTORY_LIMIT = 100

        private fun <T> Context.loadJson(file: String, adapter: JsonAdapter<List<T>>) = assets.open(file)
            .toSource()
            .buffered()
            .use(adapter::fromJson)
            .orEmpty()
    }
}