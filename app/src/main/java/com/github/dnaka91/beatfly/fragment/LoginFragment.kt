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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.extension.hidePlayer
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : DaggerFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.login_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        login.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLogin())
        }
    }

    override fun onResume() {
        super.onResume()
        hidePlayer()
    }
}
