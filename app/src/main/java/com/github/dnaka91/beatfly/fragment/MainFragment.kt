package com.github.dnaka91.beatfly.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.adapter.pager.MainPagerAdapter
import com.github.dnaka91.beatfly.extension.showPlayer
import com.github.dnaka91.beatfly.extension.startService
import com.github.dnaka91.beatfly.service.PlayerService
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : DaggerFragment() {

    @Inject
    internal lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = MainPagerAdapter(requireContext(), childFragmentManager)
        pager.offscreenPageLimit = adapter.offscreenLimit()
        pager.adapter = adapter
    }

    private var receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val playing = intent?.getBooleanExtra(PlayerService.EXTRA_STATUS_PLAYING, false) ?: false
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
        localBroadcastManager.registerReceiver(receiver, IntentFilter(PlayerService.BROADCAST_STATUS))
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
            findNavController().navigate(MainFragmentDirections.actionReview())
            true
        }
        R.id.action_request -> {
            findNavController().navigate(MainFragmentDirections.actionRequest())
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
