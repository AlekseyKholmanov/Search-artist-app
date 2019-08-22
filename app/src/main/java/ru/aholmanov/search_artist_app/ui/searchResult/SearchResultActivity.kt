package ru.aholmanov.search_artist_app.ui.searchResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_result.*
import ru.aholmanov.search_artist_app.R

class SearchResultActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        view = findViewById(R.id.webView)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onResume() {
        super.onResume()
        view.settings.javaScriptEnabled = true
        view.alpha = 0.0f
        view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                val progress = newProgress * 100
                progressBar.progress = progress
                if (newProgress > 99){
                    progressBar.animate().alpha(0.0f).duration = 800
                    view?.animate()?.alpha(1.0f)?.duration = 1000
                }
            }
        }
        loadUrl()
    }

    private fun loadUrl(){
        val url = intent.extras?.getString("url") ?: ""
        view.loadUrl(url)
    }

    lateinit var view: WebView
}