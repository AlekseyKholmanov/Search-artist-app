package ru.aholmanov.search_artist_app.ui.searchResult

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
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
        view.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.animate().alpha(0.0f).duration = 800
                view?.animate()?.alpha(1.0f)?.duration = 1000
            }
        }
        loadUrl()
    }

    private fun loadUrl() {
        val url = intent.extras?.getString("url") ?: ""
        view.loadUrl(url)
    }

    lateinit var view: WebView
}