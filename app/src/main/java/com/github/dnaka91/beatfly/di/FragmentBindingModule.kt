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

import com.github.dnaka91.beatfly.di.scope.FragmentScope
import com.github.dnaka91.beatfly.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @FragmentScope
    @ContributesAndroidInjector
    abstract fun aboutFragment(): AboutFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun historyListFragment(): HistoryListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun loginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun mainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun moderatorLicenseListFragment(): ModeratorLicenseListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun moderatorListFragment(): ModeratorListFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun requestFragment(): RequestFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun reviewFragment(): ReviewFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun songDetailFragment(): SongDetailFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun songLicenseListFragment(): SongLicenseListFragment
}
