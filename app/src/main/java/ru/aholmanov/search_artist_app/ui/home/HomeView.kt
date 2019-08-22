package ru.aholmanov.search_artist_app.ui.home

import ru.aholmanov.search_artist_app.model.Artist

interface HomeView: BaseView {
    fun updateDropdown(names: List<Artist>)
    fun showError(error: String)
}