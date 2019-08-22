package ru.aholmanov.search_artist_app.ui.home

import android.annotation.SuppressLint
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import ru.aholmanov.search_artist_app.App
import ru.aholmanov.search_artist_app.model.Artist
import ru.aholmanov.search_artist_app.repository.ArtistRepository
import ru.aholmanov.search_artist_app.repository.PreferenceRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter(private val view: HomeView) {
    init {
        App.component.inject(this)
    }

    @SuppressLint("CheckResult")
    fun subscriveOnSearch() {
        subject
            .filter { it.isNotEmpty() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                artistRepository.getArtists(it)
                    .subscribeOn(Schedulers.io())
                    .doOnError { Log.d(TAG, it.localizedMessage) }
            }
            .map { it.results.artistamches.artist }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ responce ->
                view.updateDropdown(responce)
            }, {
                Log.d(TAG, it.localizedMessage)
            })
    }

    fun getNewWord(word: String) {
        subject.onNext(word)
    }

    fun openSearchResult(artistName: String) {
        when {
            artistName.isEmpty() -> view.showError("значение не может быть пустым")
            artist?.name ?: "" == artistName -> view.startActivity(artist!!.url)
            else -> view.showError("значение выбрано не из списка")
        }
    }

    fun updateArtist(artist: Artist?) {
        this.artist = artist
    }

    var artist: Artist? = null

    private val subject by lazy { PublishSubject.create<String>() }

    private val TAG = this.javaClass.canonicalName
    @Inject
    lateinit var artistRepository: ArtistRepository
    @Inject
    lateinit var preferenceRepository: PreferenceRepository
}