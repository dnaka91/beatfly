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

import com.github.dnaka91.beatfly.extension.listAdapter
import com.github.dnaka91.beatfly.model.Moderator
import com.github.dnaka91.beatfly.model.Song
import com.github.dnaka91.beatfly.model.User
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
    fun provideSongListJsonAdapter(moshi: Moshi): JsonAdapter<List<Song>> =
        moshi.listAdapter()

    @Singleton
    @Provides
    fun provideModeratorListJsonAdapter(moshi: Moshi): JsonAdapter<List<Moderator>> =
        moshi.listAdapter()

    @Singleton
    @Provides
    fun provideUserListJsonAdapter(moshi: Moshi): JsonAdapter<List<User>> =
        moshi.listAdapter()
}
