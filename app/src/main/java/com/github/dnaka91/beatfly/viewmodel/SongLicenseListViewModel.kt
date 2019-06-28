package com.github.dnaka91.beatfly.viewmodel

import androidx.lifecycle.ViewModel
import com.github.dnaka91.beatfly.service.RadioService
import javax.inject.Inject

class SongLicenseListViewModel @Inject constructor(private val radioService: RadioService) : ViewModel() {
    val licenses get() = radioService.songLicenses()
}