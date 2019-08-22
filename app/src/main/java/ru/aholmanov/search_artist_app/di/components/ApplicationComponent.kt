package ru.aholmanov.search_artist_app.di.components

import dagger.Component
import ru.aholmanov.search_artist_app.ui.home.HomeActivity
import ru.aholmanov.search_artist_app.ui.home.HomePresenter
import ru.aholmanov.search_artist_app.ui.intro.IntroActivity
import ru.aholmanov.search_artist_app.di.modules.ApplicationModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(activity: HomeActivity)
    fun inject(presenter: HomePresenter)
}