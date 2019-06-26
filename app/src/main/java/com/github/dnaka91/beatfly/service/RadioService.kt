package com.github.dnaka91.beatfly.service

import androidx.lifecycle.LiveData
import com.github.dnaka91.beatfly.model.*

interface RadioService {
    fun currentSong(): LiveData<Song>
    fun loadHistory(): LiveData<List<Song>>
    fun loadModerators(): LiveData<List<Moderator>>

    fun songLicenses(): List<SongLicense>
    fun moderatorLicenses(): List<ModeratorLicense>
}

