package ru.aholmanov.search_artist_app.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ArtistModel(
    @SerializedName("results")
    val results: Results
) : Serializable

class Results(
    @SerializedName("artistmatches")
    val artistamches: ArtistMatches
)

class ArtistMatches(
    @SerializedName("artist")
    val artist: MutableList<Artist>
)

class Artist
    (
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String


) {
    override fun toString(): String {
        return name
    }
}
