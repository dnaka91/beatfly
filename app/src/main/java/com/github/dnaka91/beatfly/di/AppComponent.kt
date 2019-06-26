package com.github.dnaka91.beatfly.di

import com.github.dnaka91.beatfly.BeatFlyApp
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        FragmentBindingModule::class,
        ServiceBindingModule::class,
        MoshiModule::class,
        ExoPlayerModule::class
    ]
)
interface AppComponent : AndroidInjector<BeatFlyApp> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<BeatFlyApp>
}
