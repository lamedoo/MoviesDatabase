package com.lukakrodzaia.moviesdatabase.helpers

import androidx.fragment.app.Fragment

interface OpenFragmentListener {
    fun openNewFragment(fragment: Fragment, toBackStack: Boolean = true)
}