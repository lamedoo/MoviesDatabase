package com.lukakrodzaia.moviesdatabase.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lukakrodzaia.moviesdatabase.customviews.CustomSearchInput
import com.lukakrodzaia.moviesdatabase.databinding.FragmentSearchBinding
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import com.lukakrodzaia.moviesdatabase.ui.popularshows.PopularShowsAdapter
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleFragment
import com.lukakrodzaia.moviesdatabase.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var page = 1
    private var pastVisibleItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var loading = false
    private var hasMore = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            page = savedInstanceState.getInt(AppConstants.PAGE)
        }

        setSearchListener(savedInstanceState != null)
        fragmentObservers()
        clickListeners()
        setSearchResults()
        infiniteScroll()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(AppConstants.PAGE, page)
    }

    private fun clickListeners() {
        binding.backButton.setOnClickListener {
            binding.searchInput.hideKeyboard()
            requireActivity().onBackPressed()
        }
    }

    private fun fragmentObservers() {
        searchViewModel.hasMore.observe(viewLifecycleOwner, {
            hasMore = it
        })

        searchViewModel.isLoading.observe(viewLifecycleOwner, {
            binding.loading.setVisibleOrGone(it)
            binding.noInternet.setVisibleOrGone(!it)
            loading = it
        })

        searchViewModel.isInternet.observe(viewLifecycleOwner, {
            binding.noInternet.setVisibleOrGone(!it)
        })
    }

    private fun setSearchListener(isRotated: Boolean) {
        binding.searchInput.showKeyboard()

        var rotated: Boolean
        rotated = isRotated

        binding.searchInput.setQueryTextChangeListener(object : CustomSearchInput.QueryTextListener {
            override fun onQueryTextSubmit(query: String?) {}

            override fun onQueryTextChange(newText: String?) {
                if (!rotated) {
                    if (newText != null) {
                        searchViewModel.clearSearchList()
                        searchViewModel.getTvShowSearch(newText, 1)
                    } else {
                        page = 1
                        searchViewModel.clearSearchList()
                    }
                }
                rotated = false
            }

        })
    }

    private fun setSearchResults() {
        layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        searchAdapter = SearchAdapter(requireContext()) {
            val singleTitleFragment = SingleTitleFragment().applyBundle {
                putInt(AppConstants.TITLE_ID, it)
            }
            openFragmentListener?.openNewFragment(singleTitleFragment, true)
        }

        binding.rvSearch.layoutManager = layoutManager
        binding.rvSearch.adapter = searchAdapter

        searchViewModel.searchList.observe(viewLifecycleOwner, {
            searchAdapter.setItems(it)
        })
    }

    private fun infiniteScroll() {
        binding.rvSearch.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            searchViewModel.getTvShowSearch(binding.searchInput.text.toString(), page)
        }
    }

}