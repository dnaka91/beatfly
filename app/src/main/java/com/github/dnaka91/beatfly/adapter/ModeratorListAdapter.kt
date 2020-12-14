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
package com.github.dnaka91.beatfly.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.databinding.ModeratorItemBinding
import com.github.dnaka91.beatfly.model.Moderator
import splitties.dimensions.dip
import javax.inject.Inject

class ModeratorListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<ModeratorListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<Moderator>()

    class ViewHolder(val binding: ModeratorItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ModeratorItemBinding.inflate(inflater, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moderator = data[position]
        holder.apply {
            binding.title.text = moderator.name
            binding.rating.text = fragment.getString(
                R.string.mod_rating,
                moderator.rating,
                moderator.rateCount
            )

            binding.picture.load(moderator.picture.url) {
                placeholder(R.drawable.placeholder_moderator)
                transformations(RoundedCornersTransformation(fragment.requireContext().dip(4f)))
                crossfade(true)
            }
        }
    }

    fun setData(list: List<Moderator>) {
        data = list
        notifyDataSetChanged()
    }
}
