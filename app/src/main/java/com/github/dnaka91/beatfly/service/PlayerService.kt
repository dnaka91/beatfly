package com.github.dnaka91.beatfly.service

import android.content.Intent
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dnaka91.beatfly.model.Song
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import dagger.android.AndroidInjection
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Provider

class PlayerService : LifecycleService() {

    @Inject
    internal lateinit var notificationBuilder: PlayerNotificationBuilder
    @Inject
    internal lateinit var exoPlayer: ExoPlayer
    @Inject
    internal lateinit var mediaSourceFactory: Provider<ProgressiveMediaSource.Factory>
    @Inject
    internal lateinit var localBroadcastManager: LocalBroadcastManager
    @Inject
    internal lateinit var radioService: RadioService

    private var song = AtomicReference<Song>()
    private val playing = AtomicBoolean(false)

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()

        radioService.currentSong().observe(this, Observer {
            song.set(it)
            updateNotification()
        })
        notificationBuilder.start(this, song.get())

        exoPlayer.apply {
            repeatMode = Player.REPEAT_MODE_ALL
            playWhenReady = false

            (1..10).map { "%02d".format(it) to "asset:///%02d_song.mp3".format(it).toUri() }
                .map { (tag, file) -> mediaSourceFactory.get().setTag(tag).createMediaSource(file) }
                .shuffled()
                .let { ConcatenatingMediaSource(*it.toTypedArray()) }
                .let(::prepare)

            addListener(object : Player.EventListener {
                override fun onTracksChanged(trackGroups: TrackGroupArray, trackSelections: TrackSelectionArray) {
                    Log.e("MUSIC", "onTracksChanged: tracks changed, current tag: ${exoPlayer.currentTag}")

                    localBroadcastManager.sendBroadcast(Intent(BROADCAST_TRACK_CHANGED).apply {
                        putExtra(EXTRA_TRACK_CHANGED_TRACK_ID, exoPlayer.currentTag as String)
                    })
                }
            })
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent.action) {
            ACTION_PLAY -> play()
            ACTION_PAUSE -> pause()
            ACTION_STOP -> stop()
            ACTION_GET_STATUS -> getStatus()
            else -> {
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    private fun play() {
        exoPlayer.playWhenReady = true
        playing.set(true)
        updateNotification()
        getStatus()
    }

    private fun pause() {
        exoPlayer.playWhenReady = false
        playing.set(false)
        updateNotification()
        getStatus()
    }

    private fun stop() {
        pause()
        stopForeground(true)
        stopSelf()
    }

    private fun getStatus() {
        localBroadcastManager.sendBroadcast(Intent(BROADCAST_STATUS).apply {
            putExtra(EXTRA_STATUS_PLAYING, playing.get())
        })
    }

    private fun updateNotification() {
        notificationBuilder.update(song.get(), playing.get())
    }

    companion object {
        private const val prefix = "com.github.dnaka91.beatfly"

        const val ACTION_PLAY = "$prefix.ACTION_PLAY"
        const val ACTION_PAUSE = "$prefix.ACTION_PAUSE"
        const val ACTION_STOP = "$prefix.ACTION_STOP"
        const val ACTION_GET_STATUS = "$prefix.ACTION_GET_STATUS"

        const val BROADCAST_STATUS = "$prefix.BROADCAST_STATUS"
        const val EXTRA_STATUS_PLAYING = "playing"

        const val BROADCAST_TRACK_CHANGED = "$prefix.BROADCAST_TRACK_CHANGED"
        const val EXTRA_TRACK_CHANGED_TRACK_ID = "track-id"
    }
}