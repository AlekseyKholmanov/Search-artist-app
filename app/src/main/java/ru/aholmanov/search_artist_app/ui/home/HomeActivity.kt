package ru.aholmanov.search_artist_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
            when {
                artistName.isEmpty() -> showError("значение не может быть пустым")
                artist?.name ?: "" == artistName -> startActivity(artist!!.url)
                else -> showError("значение выбрано не из списка")
            }
        }

        //используется для сравнения соответствует ли последнее выбранное значение - значению в поле
        autocompleteInput.setOnItemClickListener { _, _, position, _ ->
           artist = ( adapter.getItem(position))
        }
    }

    override fun onResume() {
        super.onResume()

        autocompleteInput.addTextChangedListener(textObserver)

        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listOf())
        autocompleteInput.setAdapter(adapter)

        presenter.subscribeOnSearch()
    }

    //save artist when device rotate
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putSerializable("artist", artist)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        artist = savedInstanceState?.getSerializable("artist") as Artist
    }

    override fun showError(error: String) {
        Snackbar.make(homeActivity,error,Snackbar.LENGTH_SHORT).show()
    }

    override fun updateDropdown(names: List<Artist>) {
        adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, names)
        autocompleteInput.setAdapter(adapter)
        adapter.notifyDataSetChanged()
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

    var artist: Artist? = null

    @Inject
    lateinit var repository: ArtistRepository

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    lateinit var adapter:ArrayAdapter<Artist>
    lateinit var presenter: HomePresenter
}
