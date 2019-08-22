package ru.aholmanov.search_artist_app.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import ru.aholmanov.search_artist_app.R

class IntroActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)
            setContentView(R.layout.activity_intro)
            tabDots.setupWithViewPager(viewPager)
            viewPager.adapter = IntroAdapter(this, supportFragmentManager)
    }

    override fun onPageScrollStateChanged(state: Int) {
        tabDots.setSelectedTabIndicator(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}
    }
}