package com.github.dnaka91.beatfly.di

import android.app.Application
import android.content.Context
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dnaka91.beatfly.BeatFlyApp
import com.github.dnaka91.beatfly.service.LocalRadioService
import com.github.dnaka91.beatfly.service.RadioService
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [AppModule.Bindings::class])
class AppModule {
    @Module
    interface Bindings {
        @Singleton
        @Binds
        fun bindApplication(app: BeatFlyApp): Application

        @Singleton
        @Binds
        fun bindRadioService(radioService: LocalRadioService): RadioService
    }

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideLocalBroadcastManager(context: Context): LocalBroadcastManager =
        LocalBroadcastManager.getInstance(context)
}