package ru.aholmanov.search_artist_app.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.intro_fragment_3.*
import ru.aholmanov.search_artist_app.R

class IntroFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments?.getInt(ARG_PAGE) ?: 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view?.tag = mPage
        return when (mPage) {
            1 -> inflater.inflate(R.layout.intro_dragment_1, container, false)
            2 -> inflater.inflate(R.layout.intro_fragment_2, container, false)
            3 -> inflater.inflate(R.layout.intro_fragment_3, container, false)
            else -> throw IllegalStateException("Unknown page $mPage")

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnFinishIntro?.setOnClickListener {
            (activity as? IntroActivity)?.startHomeActivity()
        }
    }

    private var mPage: Int = 0

    companion object {
        private const val ARG_PAGE = "page"

        fun newInstance(page: Int): IntroFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = IntroFragment()
            fragment.arguments = args
            return fragment
        }
    }
}