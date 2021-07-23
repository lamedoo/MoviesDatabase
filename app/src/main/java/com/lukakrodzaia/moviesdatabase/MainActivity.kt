package com.lukakrodzaia.moviesdatabase

import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.lukakrodzaia.moviesdatabase.databinding.ActivityMainBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseActivity
import com.lukakrodzaia.moviesdatabase.ui.popular.PopularShowsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val navHost: Int
        get() = R.id.main_nav_host

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_nav_host, PopularShowsFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}