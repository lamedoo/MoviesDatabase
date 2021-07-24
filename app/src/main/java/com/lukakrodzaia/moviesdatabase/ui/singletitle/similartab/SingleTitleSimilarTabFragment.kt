package com.lukakrodzaia.moviesdatabase.ui.singletitle.similartab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSingleTitleSimilarTabBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleFragment
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleViewModel
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.applyBundle
import com.lukakrodzaia.moviesdatabase.utils.setVisibleOrGone
import org.koin.android.ext.android.inject

class SingleTitleSimilarTabFragment : BaseFragment<FragmentSingleTitleSimilarTabBinding>() {
    private val singleTitleViewModel: SingleTitleViewModel by inject()
    private lateinit var similarShowsAdapter: SimilarShowsAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var page = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var hasMore = false


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSingleTitleSimilarTabBinding
        get() = FragmentSingleTitleSimilarTabBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val id = args?.getInt(AppConstants.TITLE_ID)

        if (id != null) {
            setSimilarTitles(id)
            infiniteScroll(id)
        }

        fragmentObservers()
    }

    private fun fragmentObservers() {
        singleTitleViewModel.hasMore.observe(viewLifecycleOwner, {
            hasMore = it
        })

        singleTitleViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.setVisibleOrGone(it)
            loading = it
        })

        singleTitleViewModel.isInternet.observe(viewLifecycleOwner, {
            binding.noInternet.setVisibleOrGone(!it)
        })
    }


    private fun setSimilarTitles(id: Int) {
        singleTitleViewModel.getSimilarTitles(id, page)

        layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        similarShowsAdapter = SimilarShowsAdapter(requireContext()) {
            val singleTitleFragment = SingleTitleFragment().applyBundle {
                putInt(AppConstants.TITLE_ID, it)
            }
            openFragmentListener?.openNewFragment(singleTitleFragment, true)
        }

        binding.rvSimilarShows.layoutManager = layoutManager
        binding.rvSimilarShows.adapter = similarShowsAdapter

        singleTitleViewModel.similarTitlesList.observe(viewLifecycleOwner, {
            similarShowsAdapter.setItems(it)
        })
    }

    private fun infiniteScroll(id: Int) {
        binding.rvSimilarShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!loading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = true
                        fetchMoreNotifications(id)
                    }
                }
            }
        })
    }

    private fun fetchMoreNotifications(id: Int) {
        if (hasMore) {
            page++
            singleTitleViewModel.getSimilarTitles(id, page)
        }
    }
}