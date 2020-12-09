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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.databinding.ReviewFragmentBinding
import com.github.dnaka91.beatfly.extension.mainActivity
import com.github.dnaka91.beatfly.viewmodel.ReviewViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : BottomSheetDialogFragment() {
    private var _binding: ReviewFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ReviewViewModel>()

    private val args: ReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.header.text = getString(args.header)

        viewModel.messageError.observe(viewLifecycleOwner) { binding.reviewLayout.error = it }

        binding.submit.setOnClickListener {
            if (viewModel.submit(binding.rating.numStars, binding.review.text.toString())) {
                mainActivity()?.showSnackbar(
                    getString(R.string.review_submitted),
                    Snackbar.LENGTH_LONG
                )
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
