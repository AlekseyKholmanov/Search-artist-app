package ru.aholmanov.search_artist_app.ui.home

import ru.aholmanov.search_artist_app.model.Artist
import ru.aholmanov.search_artist_app.ui.home.BaseView

interface HomeView: BaseView {
    fun updateDropdown(names: List<Artist>)
    fun startActivity(url: String)
    fun showError(error: String)
}