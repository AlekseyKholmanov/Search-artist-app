package ru.aholmanov.search_artist_app.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_intro.*
import ru.aholmanov.search_artist_app.App
import ru.aholmanov.search_artist_app.R
import ru.aholmanov.search_artist_app.repository.PreferenceRepository
import ru.aholmanov.search_artist_app.ui.home.HomeActivity
import javax.inject.Inject

class IntroActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

        //проверям было ли просмотрено интро
        if (preferenceRepository.isViewedIntro())
            startHomeActivity()
        else {
            setContentView(R.layout.activity_intro)
            tabDots.setupWithViewPager(viewPager)
            viewPager.adapter = IntroAdapter(this, supportFragmentManager)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        tabDots.setSelectedTabIndicator(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}

    fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Inject
    lateinit var preferenceRepository: PreferenceRepository


}