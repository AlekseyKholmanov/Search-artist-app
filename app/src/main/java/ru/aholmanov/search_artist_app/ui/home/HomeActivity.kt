package ru.aholmanov.search_artist_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import ru.aholmanov.search_artist_app.App
import ru.aholmanov.search_artist_app.R
import ru.aholmanov.search_artist_app.model.Artist
import ru.aholmanov.search_artist_app.repository.ArtistRepository
import ru.aholmanov.search_artist_app.repository.PreferenceRepository
import ru.aholmanov.search_artist_app.ui.searchResult.SearchResultActivity
import java.io.Serializable
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView, ArtistAdapter.ArtistAdapterCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

        //показывать intro только 1 раз
        preferenceRepository.setIntroIsViewed()

        setContentView(R.layout.activity_home)
        presenter = HomePresenter(this)

        textInput.addTextChangedListener(textObserver)
        recycleView.layoutManager = LinearLayoutManager(this)
        artistAdapter = ArtistAdapter(listItems, this)
        recycleView.adapter = artistAdapter
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("artists", listItems as Serializable)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            val savedArtists = savedInstanceState.getSerializable("artists") as MutableList<*>
            showArtists(savedArtists as MutableList<Artist>)
        }
    }

    override fun showError(error: Int) {
        Snackbar.make(homeActivity, getString(error), Snackbar.LENGTH_SHORT).show()
    }

    override fun showEmptyPlaceholder() {
        if (recycleView.visibility != View.GONE) {
            recycleView.visibility = View.GONE
            emptyListPlaceholder.visibility = View.VISIBLE
        }
    }

    override fun showArtists(artists: MutableList<Artist>) {
        if (recycleView.visibility != View.VISIBLE) {
            recycleView.visibility = View.VISIBLE
            emptyListPlaceholder.visibility = View.GONE
        }
        listItems.clear()
        listItems.addAll(artists)
        artistAdapter.notifyDataSetChanged()
    }

    override fun artistClicked(item: Artist) {
        startActivity(item.url)
    }

    private fun startActivity(url: String) {
        val intent = Intent(applicationContext, SearchResultActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private val textObserver by lazy {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.sendNextText(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    private lateinit var artistAdapter: ArtistAdapter
    private var listItems = mutableListOf<Artist>()

    @Inject
    lateinit var repository: ArtistRepository

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    lateinit var presenter: HomePresenter
}
