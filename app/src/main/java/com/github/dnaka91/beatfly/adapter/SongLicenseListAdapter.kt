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
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.SongLicense
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.song_license_item.*
import javax.inject.Inject

class SongLicenseListAdapter @Inject constructor(private val fragment: Fragment) :
    RecyclerView.Adapter<SongLicenseListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(fragment.context)
    private var data = listOf<SongLicense>()

    class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            inflater.inflate(
                R.layout.song_license_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val license = data[position]
        holder.apply {
            name.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_name,
                    license.name.url,
                    license.name.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )
            artist.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_artist,
                    license.artist.url,
                    license.artist.value,
                    license.platform.url,
                    license.platform.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )
            cover.text = Html.fromHtml(
                fragment.getString(
                    R.string.song_license_cover,
                    license.cover.artist.url,
                    license.cover.artist.value,
                    license.cover.platform.url,
                    license.cover.platform.value
                ),
                Html.FROM_HTML_MODE_COMPACT
            )

            listOf(name, artist, cover).forEach {
                it.movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    fun setData(list: List<SongLicense>) {
        data = list
        notifyDataSetChanged()
    }
}
