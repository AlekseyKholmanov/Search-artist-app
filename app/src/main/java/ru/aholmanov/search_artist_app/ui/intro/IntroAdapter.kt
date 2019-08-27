package ru.aholmanov.search_artist_app.ui.intro

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class IntroAdapter( manager: FragmentManager) : FragmentPagerAdapter(manager) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> IntroFragment.newInstance(1)
            1 -> IntroFragment.newInstance( 2)
            2 -> IntroFragment.newInstance(3)
            else -> throw IllegalStateException("Unknown page $position")
        }
    }

    override fun getCount(): Int = 3
}