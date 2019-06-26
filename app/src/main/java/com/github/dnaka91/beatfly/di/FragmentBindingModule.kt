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
