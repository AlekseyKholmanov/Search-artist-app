package ru.aholmanov.search_artist_app

import android.app.Application
import ru.aholmanov.search_artist_app.di.components.ApplicationComponent
import ru.aholmanov.search_artist_app.di.components.DaggerApplicationComponent
import ru.aholmanov.search_artist_app.di.modules.ApplicationModule


class App: Application() {
    companion object {
        lateinit var component: ApplicationComponent
    }
    override fun onCreate() {
        super.onCreate()

        initDi()
    }

    private fun initDi(){
        component = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}