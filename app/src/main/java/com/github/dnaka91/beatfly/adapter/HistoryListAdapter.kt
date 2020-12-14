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
import com.github.dnaka91.beatfly.databinding.HistoryItemBinding
import com.github.dnaka91.beatfly.model.Song
import splitties.dimensions.dp
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class HistoryListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<HistoryListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<Song>()

    class ViewHolder(val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(HistoryItemBinding.inflate(inflater, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = data[position]
        holder.apply {
            binding.title.text = song.title
            binding.album.text = song.album
            binding.artist.text = song.artist

            val time = LocalTime.ofNanoOfDay(Duration.ofMillis(song.duration).toNanos())
            binding.duration.text = fragment.getString(
                R.string.song_duration,
                time.minute,
                time.second,
                Duration.ofNanos(time.nano.toLong()).toMillis()
            )

            binding.picture.load(song.cover.url) {
                placeholder(R.drawable.placeholder_history)
                transformations(RoundedCornersTransformation(fragment.requireContext().dp(4)))
                crossfade(true)
            }
        }
    }

    fun setData(list: List<Song>) {
        data = list
        notifyDataSetChanged()
    }
}
