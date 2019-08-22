package ru.aholmanov.search_artist_app.ui.intro

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.aholmanov.search_artist_app.R

class IntroAdapter(private val context: Context, manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IntroFragment.newInstance(
                ContextCompat.getColor(
                    context,
                    R.color.firstFragmenBackground
                ), 1
            )
            1 -> IntroFragment.newInstance(
                ContextCompat.getColor(
                    context,
                    R.color.secondFragmentBackgound
                ), 2
            )
            2 -> IntroFragment.newInstance(
                ContextCompat.getColor(
                    context,
                    R.color.thirdFragmentBackground
                ), 3
            )
            else -> throw IllegalStateException("Unknown page $position")
        }
    }

    override fun getCount(): Int = 3
}