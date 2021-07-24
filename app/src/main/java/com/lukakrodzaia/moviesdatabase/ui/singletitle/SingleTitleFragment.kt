package com.lukakrodzaia.moviesdatabase.ui.singletitle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSingleTitleBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.setImage
import com.lukakrodzaia.moviesdatabase.utils.setVisibleOrGone
import org.koin.android.ext.android.inject
import kotlin.math.abs

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
            fragmentListeners(id)
        }

        fragmentObservers()
    }

    private fun fragmentListeners(id: Int) {
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

        binding.retryButton.setOnClickListener {
            singleTitleViewModel.getSingleTitle(id)
        }

        binding.collapsingToolbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appbar, verticalOffset ->
            var offsetPercent = 0F
            var toolbarAlphaPercent = 0F

            try {
                offsetPercent = (abs(verticalOffset).toFloat() * 100F) / (appbar.totalScrollRange.toFloat())
                toolbarAlphaPercent = 1F - ((offsetPercent) / 90F)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }

            val collapsedToolbarAlpha = if (offsetPercent >= 80F) {
                (-4F) + ((offsetPercent) / 20F)
            } else {
                0F
            }


            if (abs(verticalOffset) == appbar.totalScrollRange) {
                binding.titleName.alpha = 0F
                binding.titleNameTop.alpha = 1F
            } else {
                binding.titleName.alpha = toolbarAlphaPercent
                binding.titleNameTop.alpha = collapsedToolbarAlpha
            }
        })
    }

    private fun fragmentObservers() {
        singleTitleViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.setVisibleOrGone(it)
            binding.mainContainer.setVisibleOrGone(!it)
            binding.noInternet.setVisibleOrGone(!it)
        })

        singleTitleViewModel.isInternet.observe(viewLifecycleOwner, {
            binding.noInternet.setVisibleOrGone(!it)
            binding.mainContainer.setVisibleOrGone(it)
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
            binding.titleNameTop.text = it.name
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