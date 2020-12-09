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

import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.databinding.SongLicenseItemBinding
import com.github.dnaka91.beatfly.model.SongLicense
import javax.inject.Inject

class SongLicenseListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<SongLicenseListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<SongLicense>()

    class ViewHolder(val binding: SongLicenseItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(SongLicenseItemBinding.inflate(inflater, parent, false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val license = data[position]
        holder.apply {
            binding.name.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_name,
                    license.name.url,
                    license.name.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )
            binding.artist.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_artist,
                    license.artist.url,
                    license.artist.value,
                    license.platform.url,
                    license.platform.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )
            binding.cover.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_cover,
                    license.cover.artist.url,
                    license.cover.artist.value,
                    license.cover.platform.url,
                    license.cover.platform.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )

            listOf(binding.name, binding.artist, binding.cover).forEach {
                it.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    fun setData(list: List<SongLicense>) {
        data = list
        notifyDataSetChanged()
    }
}
