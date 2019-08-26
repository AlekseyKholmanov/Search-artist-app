package ru.aholmanov.search_artist_app.ui.intro

import android.animation.ArgbEvaluator
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
            tabDots.setupWithViewPager(viewPager, true)
            viewPager.addOnPageChangeListener(this)
            viewPager.adapter = IntroAdapter(supportFragmentManager)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (positionOffset != 0f) {
            val color = evaluator.evaluate(positionOffset, this.getColor(colors[position]), this.getColor(colors[position + 1])) as Int
            viewPager.setBackgroundColor(color)
        } else {
            viewPager.setBackgroundColor(this.getColor(colors[position]))
        }
    }

    override fun onPageSelected(position: Int) {}

    fun startHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    private val colors: Array<Int> =
        arrayOf(R.color.firstFragmentBackground,R.color.secondFragmentBackground,R.color.thirdFragmentBackground)

    private val evaluator = ArgbEvaluator()
}
