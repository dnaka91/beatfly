package com.github.dnaka91.beatfly.di

import com.github.dnaka91.beatfly.activity.ActionBarActivity
import com.github.dnaka91.beatfly.activity.MainActivity
import com.github.dnaka91.beatfly.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun actionBarActivity(): ActionBarActivity
}