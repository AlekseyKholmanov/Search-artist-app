package ru.aholmanov.search_artist_app.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class IntroActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
    }
}