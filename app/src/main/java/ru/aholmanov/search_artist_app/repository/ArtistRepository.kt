package ru.aholmanov.search_artist_app.repository

import io.reactivex.Observable
import ru.aholmanov.search_artist_app.model.ArtistModel
import ru.aholmanov.search_artist_app.api.ApiService
import javax.inject.Inject

class ArtistRepository @Inject constructor(private val service: ApiService) {

    fun getArtists(artistName: String = ""): Observable<ArtistModel> {
        return service.getArtists(artistName)
    }
}