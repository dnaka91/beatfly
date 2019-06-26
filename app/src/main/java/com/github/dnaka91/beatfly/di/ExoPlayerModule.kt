package com.github.dnaka91.beatfly.di

import android.content.Context
import com.github.dnaka91.beatfly.R
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExoPlayerModule {
    @Singleton
    @Provides
    fun provideDataSourceFactory(context: Context): DataSource.Factory =
        DefaultDataSourceFactory(context, Util.getUserAgent(context, context.getString(R.string.app_name)))

    @Provides
    fun provideMediaSourceFactory(dataSourceFactory: DataSource.Factory): ProgressiveMediaSource.Factory =
        ProgressiveMediaSource.Factory(dataSourceFactory)
}