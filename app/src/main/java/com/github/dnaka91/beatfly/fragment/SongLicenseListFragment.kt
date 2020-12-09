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
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.dnaka91.beatfly.adapter.SongLicenseListAdapter
import com.github.dnaka91.beatfly.databinding.SongLicenseListFragmentBinding
import com.github.dnaka91.beatfly.viewmodel.SongLicenseListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SongLicenseListFragment : Fragment() {
    private var _binding: SongLicenseListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<SongLicenseListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SongLicenseListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = SongLicenseListAdapter(this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )

        adapter.setData(viewModel.licenses)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
