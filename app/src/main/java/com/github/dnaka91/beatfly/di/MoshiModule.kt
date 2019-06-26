package com.github.dnaka91.beatfly.di

import com.github.dnaka91.beatfly.extension.adapter
import com.github.dnaka91.beatfly.extension.listAdapter
import com.github.dnaka91.beatfly.model.Moderator
import com.github.dnaka91.beatfly.model.Song
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MoshiModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideSongListJsonAdapter(moshi: Moshi): JsonAdapter<List<Song>> = moshi.listAdapter()

    @Singleton
    @Provides
    fun provideModeratorListJsonAdapter(moshi: Moshi): JsonAdapter<List<Moderator>> = moshi.listAdapter()
}