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
