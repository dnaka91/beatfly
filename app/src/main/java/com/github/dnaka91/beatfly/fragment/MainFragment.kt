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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.github.dnaka91.beatfly.AppPreferences
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.adapter.pager.MainPagerAdapter
import com.github.dnaka91.beatfly.extension.showPlayer
import com.github.dnaka91.beatfly.extension.startService
import com.github.dnaka91.beatfly.service.PlayerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    internal lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = MainPagerAdapter(requireContext(), childFragmentManager)
        pager.offscreenPageLimit = adapter.offscreenLimit()
        pager.adapter = adapter
    }

    private var receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val playing =
                intent?.getBooleanExtra(PlayerService.EXTRA_STATUS_PLAYING, false) ?: false
            requireActivity().fab.setImageResource(
                if (playing) R.drawable.ic_pause_24
                else R.drawable.ic_play_arrow_24
            )

            showPlayer {
                startService<PlayerService>(
                    if (playing) PlayerService.ACTION_PAUSE
                    else PlayerService.ACTION_PLAY
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!AppPreferences.logged_in) {
            startService<PlayerService>(PlayerService.ACTION_STOP)
            findNavController().navigate(MainFragmentDirections.actionLogout())
            return
        }

        localBroadcastManager.registerReceiver(
            receiver,
            IntentFilter(PlayerService.BROADCAST_STATUS)
        )
        startService<PlayerService>(PlayerService.ACTION_GET_STATUS)
    }

    override fun onPause() {
        super.onPause()
        localBroadcastManager.unregisterReceiver(receiver)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_review -> {
            val headerRes = when (pager.currentItem) {
                PAGER_INDEX_MODS -> R.string.review_header_moderator
                else -> R.string.review_header_playlist
            }
            findNavController().navigate(MainFragmentDirections.actionReview(headerRes))
            true
        }
        R.id.action_request -> {
            findNavController().navigate(MainFragmentDirections.actionRequest())
            true
        }
        R.id.action_logout -> {
            AppPreferences.logged_in = false
            startService<PlayerService>(PlayerService.ACTION_STOP)
            findNavController().navigate(MainFragmentDirections.actionLogout())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PAGER_INDEX_MODS = 2
    }
}
