package com.github.dnaka91.beatfly.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dnaka91.beatfly.service.RadioService
import javax.inject.Inject

class HistoryListViewModel @Inject constructor(radioService: RadioService) : ViewModel() {
    val history = radioService.loadHistory()
}