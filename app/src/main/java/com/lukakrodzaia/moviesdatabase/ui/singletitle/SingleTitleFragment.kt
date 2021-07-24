package com.lukakrodzaia.moviesdatabase.ui.singletitle

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSingleTitleBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.setImage
import com.lukakrodzaia.moviesdatabase.utils.setVisibleOrGone
import org.koin.android.ext.android.inject

class SingleTitleFragment : BaseFragment<FragmentSingleTitleBinding>() {
    private val singleTitleViewModel: SingleTitleViewModel by inject()
    private lateinit var pagerAdapter: SingleTitleDetailsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSingleTitleBinding
        get() = FragmentSingleTitleBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val id = args?.getInt(AppConstants.TITLE_ID)

        if (id != null) {
            getSingleTitleData(id)
            setViewPager(id)
        }

        clickListeners()
        fragmentObservers()
    }

    private fun clickListeners() {
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "This is my text to send.")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun fragmentObservers() {
        singleTitleViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.setVisibleOrGone(it)
            binding.mainContainer.setVisibleOrGone(!it)
        })

        singleTitleViewModel.isInternet.observe(viewLifecycleOwner, {
            binding.noInternet.setVisibleOrGone(!it)
        })
    }

    private fun setViewPager(id: Int) {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(resources.getString(R.string.details)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(resources.getString(R.string.similar)))

        pagerAdapter = SingleTitleDetailsAdapter(childFragmentManager, id)

        binding.pager.adapter = pagerAdapter
        binding.pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.tabLayout.addOnTabSelectedListener(OnTabSelectedListener())
    }

    private fun getSingleTitleData(id: Int) {
        singleTitleViewModel.getSingleTitle(id)

        singleTitleViewModel.singleTitleData.observe(viewLifecycleOwner, {
            binding.titleCover.setImage(it.cover)
            binding.titleName.text = it.name
            binding.titleRating.setDetailInfo(it.rating)
            binding.titleDate.setDetailInfo(it.date)
            binding.titleLength.setDetailInfo(it.length)
        })
    }

    inner class OnTabSelectedListener : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            binding.pager.currentItem = tab.position
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {}

        override fun onTabReselected(tab: TabLayout.Tab?) {}

    }
}