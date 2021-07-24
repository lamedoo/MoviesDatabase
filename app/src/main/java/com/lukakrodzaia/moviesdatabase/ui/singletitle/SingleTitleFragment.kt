package com.lukakrodzaia.moviesdatabase.ui.singletitle

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSingleTitleBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.setImage
import com.lukakrodzaia.moviesdatabase.utils.setVisibleOrGone
import org.koin.android.ext.android.inject

class SingleTitleFragment : BaseFragment<FragmentSingleTitleBinding>() {
    private val singleTitleViewModel: SingleTitleViewModel by inject()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSingleTitleBinding
        get() = FragmentSingleTitleBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val id = args?.getInt(AppConstants.TITLE_ID)

        if (id != null) {
            getSingleTitleData(id)
        }

        fragmentObservers()
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

    private fun getSingleTitleData(id: Int) {
        singleTitleViewModel.getSingleTitle(id)

        singleTitleViewModel.singleTitleData.observe(viewLifecycleOwner, {
            binding.titleCover.setImage(it.cover)
            binding.titleName.text = it.name
            binding.titleOverview.text = it.overview
            binding.titleRating.setDetailInfo(it.rating)
            binding.titleDate.setDetailInfo(it.date)
            binding.titleLength.setDetailInfo(it.length)
        })
    }
}