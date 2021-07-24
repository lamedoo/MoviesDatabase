package com.lukakrodzaia.moviesdatabase.ui.baseclasses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.lukakrodzaia.moviesdatabase.helpers.OpenFragmentListener

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), OpenFragmentListener {
    abstract val navHost: Int
    lateinit var binding: VB
    abstract fun getViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        setContentView(binding.root)
    }

    override fun openNewFragment(fragment: Fragment, toBackStack: Boolean) {
        val fragmentManager = supportFragmentManager.beginTransaction()
            .add(navHost, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        if (toBackStack) {
            fragmentManager
                .addToBackStack(null)
                .commit()
        } else {
            fragmentManager
                .commit()
        }
    }
}