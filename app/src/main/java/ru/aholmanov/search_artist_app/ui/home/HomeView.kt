package ru.aholmanov.search_artist_app.ui.home

import ru.aholmanov.search_artist_app.model.Artist

interface HomeView: BaseView {
    fun showArtists(artists: MutableList<Artist>)
    fun showError(resId: Int)
    fun showEmptyPlaceholder()
}