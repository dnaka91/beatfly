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
import androidx.lifecycle.Observer
import coil.load
import coil.transform.RoundedCornersTransformation
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.databinding.SongDetailFragmentBinding
import com.github.dnaka91.beatfly.service.RadioService
import dagger.hilt.android.AndroidEntryPoint
import splitties.dimensions.dp
import javax.inject.Inject

@AndroidEntryPoint
class SongDetailFragment : Fragment() {
    private var _binding: SongDetailFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var radioService: RadioService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SongDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        radioService.currentSong().observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            binding.title.text = it.title
            binding.album.text = it.album
            binding.artist.text = it.artist

            binding.picture.load(it.cover.url) {
                placeholder(R.drawable.placeholder_album)
                transformations(RoundedCornersTransformation(requireContext().dp(8)))
                crossfade(true)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
