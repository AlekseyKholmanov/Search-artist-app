package ru.aholmanov.search_artist_app.di.modules

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import ru.aholmanov.search_artist_app.api.ApiService
import ru.aholmanov.search_artist_app.api.ApiServiceFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    private val STORAGE_NAME = "PREFERENCE_STORAGE"

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun provideNewsService(gson: Gson): ApiService {
        return ApiServiceFactory.createNewsService(gson)
    }

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences {
        return context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
    }

}