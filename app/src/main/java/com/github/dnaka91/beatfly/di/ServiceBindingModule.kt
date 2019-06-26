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