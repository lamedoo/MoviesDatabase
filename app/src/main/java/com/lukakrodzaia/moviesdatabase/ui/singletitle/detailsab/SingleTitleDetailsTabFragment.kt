package com.lukakrodzaia.moviesdatabase.ui.singletitle.detailsab

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSingleTitleDetailsTabBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleViewModel
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingleTitleDetailsTabFragment : BaseFragment<FragmentSingleTitleDetailsTabBinding>() {
    private val singleTitleViewModel: SingleTitleViewModel by viewModel()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSingleTitleDetailsTabBinding
        get() = FragmentSingleTitleDetailsTabBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val id = args?.getInt(AppConstants.TITLE_ID)

        if (id != null) {
            if (savedInstanceState == null) {
                singleTitleViewModel.getSingleTitle(id)
            }
        }
        setTitleDetails()
    }

    private fun setTitleDetails() {
        singleTitleViewModel.singleTitleData.observe(viewLifecycleOwner, {
            binding.titleRating.setDetailInfo(it.rating)
            binding.titleDate.setDetailInfo(it.date)
            binding.titleLength.setDetailInfo(it.length)
            binding.titleOverview.text = it.overview
            binding.titleGenres.text = it.genres
            binding.titleDuration.text = "${resources.getString(R.string.seasons)} ${it.seasons}, ${resources.getString(R.string.episodes)} ${it.episodes}"
        })
    }
}