package ru.aholmanov.search_artist_app.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import ru.aholmanov.search_artist_app.model.ArtistModel

interface ApiService {

    @GET("?method=artist.search")
    fun getArtists( @Query("artist") artistName: String): Observable<ArtistModel>
}