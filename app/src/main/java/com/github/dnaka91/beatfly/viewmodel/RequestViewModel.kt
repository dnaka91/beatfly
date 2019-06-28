package com.github.dnaka91.beatfly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class RequestViewModel @Inject constructor() : ViewModel() {
    private val _songError = MutableLiveData<String>()
    private val _artistError = MutableLiveData<String>()

    val songError: LiveData<String>
        get() = _songError

    val artistError: LiveData<String>
        get() = _artistError

    fun submit(song: String, artist: String): Boolean {
        if (song.isBlank()) {
            _songError.postValue("Song missing")
            return false
        } else {
            _songError.postValue(null)
        }
        if (artist.isBlank()) {
            _artistError.postValue("Please provide the artist's name")
            return false
        } else {
            _artistError.postValue(null)
        }
        return true
    }
}