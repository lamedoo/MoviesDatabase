package com.lukakrodzaia.moviesdatabase.ui.singletitle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.lukakrodzaia.moviesdatabase.ui.singletitle.detailsab.SingleTitleDetailsTabFragment
import com.lukakrodzaia.moviesdatabase.ui.singletitle.similartab.SingleTitleSimilarTabFragment
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.applyBundle

class SingleTitleDetailsAdapter(fa: FragmentManager, private val id: Int): FragmentStatePagerAdapter(fa) {

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> setIdToFragment(SingleTitleDetailsTabFragment())
            1 -> setIdToFragment(SingleTitleSimilarTabFragment())
            else -> SingleTitleDetailsTabFragment()
        }
    }

    private fun setIdToFragment(fragment: Fragment): Fragment {
        fragment.applyBundle {
            putInt(AppConstants.TITLE_ID, id)
        }
        return fragment
    }
}