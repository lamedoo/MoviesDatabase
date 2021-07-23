package com.lukakrodzaia.moviesdatabase.ui.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukakrodzaia.moviesdatabase.databinding.FragmentPopularShowsBinding
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularShowsFragment: BaseFragment<FragmentPopularShowsBinding>() {
    private val popularShowsViewModel: PopularShowsViewModel by viewModel()
    private lateinit var popularShowsAdapter: PopularShowsAdapter
    private lateinit var layoutManager: LinearLayoutManager

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
    }

    private fun setPopularShowsRecycler() {
        popularShowsViewModel.fetchPopularShows(page)

        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        popularShowsAdapter = PopularShowsAdapter(requireContext()) {

        }

        binding.rvPopularShows.layoutManager = layoutManager
        binding.rvPopularShows.adapter = popularShowsAdapter

        popularShowsViewModel.popularShowsList.observe(viewLifecycleOwner, {
            popularShowsAdapter.setItems(it)
        })
    }
}