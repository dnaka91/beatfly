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
package com.github.dnaka91.beatfly.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.di.ViewModelFactory
import com.github.dnaka91.beatfly.extension.hidePlayer
import com.github.dnaka91.beatfly.model.LoginResponse
import com.github.dnaka91.beatfly.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    internal lateinit var inputMethodManager: InputMethodManager

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<LoginViewModel>
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.login_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.usernameError.observe(viewLifecycleOwner, Observer { usernameLayout.error = it })
        viewModel.passwordError.observe(viewLifecycleOwner, Observer { passwordLayout.error = it })

        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login.performClick()
            }
            true
        }

        login.setOnClickListener {
            val usernameValue = username.text.toString()
            val passwordValue = password.text.toString()
            when (val response = viewModel.login(usernameValue, passwordValue)) {
                is LoginResponse.Success -> {
                    viewModel.setLoggedIn(response)
                    findNavController().navigate(LoginFragmentDirections.actionLogin())
                }
                is LoginResponse.Error -> {
                    username.setText("")
                    password.setText("")
                    username.requestFocus()
                    inputMethodManager.showSoftInput(username, InputMethodManager.SHOW_FORCED)
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (viewModel.isLoggedIn()) {
            findNavController().navigate(LoginFragmentDirections.actionLogin())
        }
    }

    override fun onResume() {
        super.onResume()
        hidePlayer()
    }
}
