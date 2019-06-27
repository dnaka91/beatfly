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
package com.github.dnaka91.beatfly.di

import android.app.Service
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.github.dnaka91.beatfly.di.scope.ServiceScope
import com.github.dnaka91.beatfly.service.PlayerService
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBindingModule {
    @ServiceScope
    @ContributesAndroidInjector(modules = [PlayerServiceModule::class])
    abstract fun playerService(): PlayerService
}

@Module(includes = [PlayerServiceModule.Bindings::class])
class PlayerServiceModule {
    @Module
    interface Bindings {
        @ServiceScope
        @Binds
        fun bindService(service: PlayerService): Service
    }

    @ServiceScope
    @Provides
    fun provideNotificationManagerCompat(service: Service): NotificationManagerCompat =
        NotificationManagerCompat.from(service)

    @ServiceScope
    @Provides
    fun provideExoPlayer(context: Context): ExoPlayer = ExoPlayerFactory.newSimpleInstance(context)
}
