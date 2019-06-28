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
package com.github.dnaka91.beatfly.service

import android.app.*
import android.content.Context
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.github.dnaka91.beatfly.R
import com.github.dnaka91.beatfly.model.Song
import org.jetbrains.anko.intentFor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerNotificationBuilder @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManagerCompat
) {
    fun start(service: Service, song: Song?) {
        service.startForeground(NOTIFICATION_ID, build(song, false))
    }

    fun update(song: Song?, playing: Boolean) {
        notificationManager.notify(NOTIFICATION_ID, build(song, playing))
    }

    private fun build(song: Song?, playing: Boolean): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
        val playPauseAction = if (playing) pauseAction() else playAction()

        return builder.setContentTitle(song?.title ?: "-")
            .setContentText(song?.artist ?: "-")
            .setSubText(song?.album ?: "-")
            .setSmallIcon(R.drawable.ic_headset_24)
            .setColor(context.getColor(R.color.colorPrimary))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .addAction(playPauseAction)
            .addAction(stopAction())
            .setOnlyAlertOnce(true)
            .setAutoCancel(false)
            .setStyle(MediaStyle().setShowActionsInCompactView(0, 1))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        if (notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL) != null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    NOTIFICATION_CHANNEL,
                    context.getString(R.string.notification_channel),
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
    }

    private fun playAction() = buildPendingIntent(
        R.drawable.ic_play_arrow_36_sharp,
        R.string.notification_play,
        PlayerService.ACTION_PLAY
    )

    private fun pauseAction() = buildPendingIntent(
        R.drawable.ic_pause_36_sharp,
        R.string.notification_pause,
        PlayerService.ACTION_PAUSE
    )

    private fun stopAction() = buildPendingIntent(
        R.drawable.ic_stop_36_sharp,
        R.string.notification_stop,
        PlayerService.ACTION_STOP
    )

    private fun buildPendingIntent(@DrawableRes icon: Int, @StringRes title: Int, action: String) =
        context.intentFor<PlayerService>()
            .also { it.action = action }
            .let { PendingIntent.getService(context, 0, it, 0) }
            .let { NotificationCompat.Action(icon, context.getString(title), it) }


    companion object {
        private const val NOTIFICATION_CHANNEL = "com.github.dnaka91.beatfly.NOW_PLAYING"
        private const val NOTIFICATION_ID = 1000
    }
}
