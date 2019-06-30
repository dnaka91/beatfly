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
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.dnaka91.beatfly.R
import javax.inject.Inject

class ReviewViewModel @Inject constructor(private val context: Context) : ViewModel() {
    private val _messageError = MutableLiveData<String>()

    val messageError: LiveData<String>
        get() = _messageError

    fun submit(rating: Int, message: String): Boolean {
        val errors = mutableMapOf<String, String?>(
            "message" to null
        )
        when {
            message.isBlank() ->
                errors["message"] = context.getString(R.string.review_message_blank)
        }

        _messageError.postValue(errors["message"])

        return errors.values.all { it == null }
    }
}
