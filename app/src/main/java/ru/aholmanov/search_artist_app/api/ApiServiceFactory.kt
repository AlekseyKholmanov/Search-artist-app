package ru.aholmanov.search_artist_app.api

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.aholmanov.search_artist_app.BuildConfig


object ApiServiceFactory {
    fun createNewsService(gson: Gson): ApiService {
        return createRetrofit(gson)
            .create(ApiService::class.java)
    }

    private fun createRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createHttpClient())
            .build()
    }

    private fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(Interseptor())
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                }
            }
            .build()
    }
}