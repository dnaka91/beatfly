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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.di.ViewModelFactory
import com.github.dnaka91.beatfly.di.base.DaggerBottomSheetDialogFragment
import com.github.dnaka91.beatfly.viewmodel.RequestViewModel
import kotlinx.android.synthetic.main.request_fragment.*
import javax.inject.Inject

class RequestFragment : DaggerBottomSheetDialogFragment() {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory<RequestViewModel>
    private lateinit var viewModel: RequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.request_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.songError.observe(this, Observer { songLayout.error = it })
        viewModel.artistError.observe(this, Observer { artistLayout.error = it })

        submit.setOnClickListener {
            if (viewModel.submit(song.text.toString(), artist.text.toString())) {
                dismiss()
            }
        }
    }
}
