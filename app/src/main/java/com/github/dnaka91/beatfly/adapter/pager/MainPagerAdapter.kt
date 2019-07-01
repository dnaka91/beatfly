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
package com.github.dnaka91.beatfly.adapter.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.fragment.HistoryListFragment
import com.github.dnaka91.beatfly.fragment.ModeratorListFragment
import com.github.dnaka91.beatfly.fragment.ReviewListFragment
import com.github.dnaka91.beatfly.fragment.SongDetailFragment
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Inject
import kotlin.math.max

class MainPagerAdapter @Inject constructor(ctx: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = listOf<Pair<String, () -> Fragment>>(
        ctx.getString(R.string.main_tab_header_song) to { SongDetailFragment() },
        ctx.getString(R.string.main_tab_header_history) to { HistoryListFragment() },
        if (ctx.defaultSharedPreferences.getBoolean("moderator", false)) {
            ctx.getString(R.string.main_tab_header_reviews) to { ReviewListFragment() }
        } else {
            ctx.getString(R.string.main_tab_header_moderators) to { ModeratorListFragment() }
        }
    )

    override fun getPageTitle(position: Int): String = fragments[position].first

    override fun getItem(position: Int) = fragments[position].second()

    override fun getCount() = fragments.size

    fun offscreenLimit() = max(1, fragments.size - 1)
}
