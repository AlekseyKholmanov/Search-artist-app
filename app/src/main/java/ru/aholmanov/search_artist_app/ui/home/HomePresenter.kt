package ru.aholmanov.search_artist_app.ui.home

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.aholmanov.search_artist_app.App
import ru.aholmanov.search_artist_app.R
import ru.aholmanov.search_artist_app.repository.ArtistRepository
import ru.aholmanov.search_artist_app.repository.PreferenceRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter(private val view: HomeView) {
    init {
        App.component.inject(this)
    }

    fun sendNextText(word: String) {
        observable = Observable.just(word)
        search()
    }

    @SuppressLint("CheckResult")
    private fun search() {
        observable!!
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                artistRepository.getArtists(it)
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.d(tag, it.localizedMessage) }
            }
            .map { it.results.artistamches.artist }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responce ->
                if (responce.isEmpty())
                    view.showEmptyPlaceholder()
                else
                    view.showArtists(responce)
            }, {
                view.showError(R.string.error_search)
            })
    }

    private var observable: Observable<String>? = null

    private val tag = this.javaClass.canonicalName
    @Inject
    lateinit var artistRepository: ArtistRepository
    @Inject
    lateinit var preferenceRepository: PreferenceRepository
}