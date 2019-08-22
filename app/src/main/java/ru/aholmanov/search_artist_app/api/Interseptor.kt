package ru.aholmanov.search_artist_app.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.aholmanov.search_artist_app.BuildConfig


class Interseptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newUrl = original.url().newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("format", "json")
            .build()
        val request = original.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(request)
    }
}