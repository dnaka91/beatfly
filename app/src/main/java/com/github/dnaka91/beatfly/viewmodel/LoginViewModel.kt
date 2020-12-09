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
import com.github.dnaka91.beatfly.AppPreferences
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.LoginResponse
import com.github.dnaka91.beatfly.service.RadioService
import dagger.hilt.android.qualifiers.ActivityContext
import splitties.preferences.edit
import javax.inject.Inject

class LoginViewModel @ViewModelInject constructor(
    @ActivityContext   private val context: Context,
    private val radioService: RadioService
) : ViewModel() {
    private val _usernameError = MutableLiveData<String>()
    private val _passwordError = MutableLiveData<String>()

    val usernameError: LiveData<String>
        get() = _usernameError

    val passwordError: LiveData<String>
        get() = _passwordError

    fun login(username: String, password: String): LoginResponse {
        var response: LoginResponse = LoginResponse.Error
        val errors = mutableMapOf<String, String?>(
            "username" to null,
            "password" to null
        )
        when {
            username.isBlank() ->
                errors["username"] = context.getString(R.string.login_username_blank)
            password.isBlank() ->
                errors["password"] = context.getString(R.string.login_password_blank)
            else -> {
                response = radioService.login(username, password)
                if (response == LoginResponse.Error) {
                    val mismatch = context.getString(R.string.login_mismatch)
                    errors["username"] = mismatch
                    errors["password"] = mismatch
                }
            }
        }

        _usernameError.postValue(errors["username"])
        _passwordError.postValue(errors["password"])

        return response
    }

    fun setLoggedIn(response: LoginResponse.Success) {
        AppPreferences.edit {
            logged_in = true
            moderator = response.moderator
        }
    }

    fun isLoggedIn() = AppPreferences.logged_in
}
