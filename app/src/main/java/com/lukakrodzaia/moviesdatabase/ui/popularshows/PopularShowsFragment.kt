package com.lukakrodzaia.moviesdatabase.ui.popularshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.databinding.FragmentPopularShowsBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.ui.search.SearchFragment
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleFragment
import com.lukakrodzaia.moviesdatabase.utils.AppConstants
import com.lukakrodzaia.moviesdatabase.utils.applyBundle
import com.lukakrodzaia.moviesdatabase.utils.setVisibleOrGone
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularShowsFragment: BaseFragment<FragmentPopularShowsBinding>() {
    private val popularShowsViewModel: PopularShowsViewModel by viewModel()
    private lateinit var popularShowsAdapter: PopularShowsAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var page = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var hasMore = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPopularShowsBinding
        get() = FragmentPopularShowsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPopularShowsRecycler()
        fragmentObservers()
        infiniteScroll()
        clickListeners()
    }

    private fun clickListeners() {
        binding.searchButton.setOnClickListener {
            openFragmentListener?.openNewFragment(SearchFragment())
        }

        binding.retryButton.setOnClickListener {
            popularShowsViewModel.fetchPopularShows(page)
        }
    }

    private fun fragmentObservers() {
        popularShowsViewModel.hasMore.observe(viewLifecycleOwner, {
            hasMore = it
        })

        popularShowsViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.setVisibleOrGone(it)
            binding.noInternet.setVisibleOrGone(!it)
            loading = it
        })

        popularShowsViewModel.isInternet.observe(viewLifecycleOwner, {
            binding.noInternet.setVisibleOrGone(!it)
        })
    }

    private fun setPopularShowsRecycler() {
        popularShowsViewModel.fetchPopularShows(page)

        layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        popularShowsAdapter = PopularShowsAdapter(requireContext()) {
            val singleTitleFragment = SingleTitleFragment().applyBundle {
                putInt(AppConstants.TITLE_ID, it)
            }
            openFragmentListener?.openNewFragment(singleTitleFragment, true)
        }

        binding.rvPopularShows.layoutManager = layoutManager
        binding.rvPopularShows.adapter = popularShowsAdapter

        popularShowsViewModel.popularShowsList.observe(viewLifecycleOwner, {
            popularShowsAdapter.setItems(it)
        })
    }

    private fun infiniteScroll() {
        binding.rvPopularShows.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!loading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        loading = true
                        fetchMoreNotifications()
                    }
                }
            }
        })
    }

    private fun fetchMoreNotifications() {
        if (hasMore) {
            page++
            popularShowsViewModel.fetchPopularShows(page)
        }
    }
}