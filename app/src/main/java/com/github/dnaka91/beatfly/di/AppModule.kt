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
import android.view.inputmethod.InputMethodManager
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.dnaka91.beatfly.service.LocalRadioService
import com.github.dnaka91.beatfly.service.RadioService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [AppModule.Bindings::class])
@InstallIn(SingletonComponent::class)
class AppModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Bindings {

        @Singleton
        @Binds
        fun bindRadioService(radioService: LocalRadioService): RadioService
    }

    @Singleton
    @Provides
    fun provideLocalBroadcastManager(@ApplicationContext context: Context): LocalBroadcastManager =
        LocalBroadcastManager.getInstance(context)

    @Singleton
    @Provides
    fun provideNotificationManagerCompat(@ApplicationContext context: Context): NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    @Singleton
    @Provides
    fun provideInputMethodManager(@ApplicationContext context: Context): InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
}
