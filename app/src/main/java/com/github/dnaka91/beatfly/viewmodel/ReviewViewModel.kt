package com.github.dnaka91.beatfly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ReviewViewModel @Inject constructor() : ViewModel() {
    private val _messageError = MutableLiveData<String>()

    val messageError: LiveData<String>
        get() = _messageError

    fun submit(rating: Int, message: String): Boolean {
        if (message.isBlank()) {
            _messageError.postValue("Please write a message")
            return false
        } else {
            _messageError.postValue(null)
        }
        return true
    }
}