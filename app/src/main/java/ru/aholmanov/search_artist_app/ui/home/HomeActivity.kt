package ru.aholmanov.search_artist_app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_home.*
import ru.aholmanov.search_artist_app.App
import ru.aholmanov.search_artist_app.R
import ru.aholmanov.search_artist_app.model.Artist
import ru.aholmanov.search_artist_app.repository.ArtistRepository
import ru.aholmanov.search_artist_app.repository.PreferenceRepository
import ru.aholmanov.search_artist_app.ui.searchResult.SearchResultActivity
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HomeView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

        //показывать intro только 1 раз
        preferenceRepository.setIntroIsViewed()

        setContentView(R.layout.activity_home)
        presenter = HomePresenter(this)

        search.setOnClickListener{
            val artistName = autocompleteInput.text.toString()
            presenter.openSearchResult(artistName)
        }

        //используется для сравнения соответствует ли последнее выбранное значение - значению в поле
        autocompleteInput.setOnItemClickListener { _, _, position, _ ->
           presenter.updateArtist( adapter.getItem(position))
        }
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()

        autocompleteInput.addTextChangedListener(textObserver)

        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf())
        autocompleteInput.setAdapter(adapter)

        presenter.subscriveOnSearch()
    }

    override fun showError(error: String) {
        Snackbar.make(homeActivity,error,Snackbar.LENGTH_SHORT).show()
    }

    override fun updateDropdown(names: List<Artist>) {
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, names)
        autocompleteInput.setAdapter(adapter)
        adapter.notifyDataSetChanged()
    }

    override fun startActivity(url: String) {
        val intent = Intent(applicationContext, SearchResultActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private val textObserver by lazy {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.getNewWord(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    @Inject
    lateinit var repository: ArtistRepository

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    lateinit var adapter:ArrayAdapter<Artist>
    lateinit var presenter: HomePresenter
}
