package com.lukakrodzaia.moviesdatabase.ui.popularshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.utils.toPopularListModel
import kotlinx.coroutines.launch

class PopularShowsViewModel: BaseViewModel() {
    private val fetchPopularShows: MutableList<PopularListModel> = ArrayList()
    private val _popularShowsList = MutableLiveData<List<PopularListModel>>()
    val popularShowsList: LiveData<List<PopularListModel>> = _popularShowsList

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    fun fetchPopularShows(page: Int) {
        viewModelScope.launch {
            loading()
            when (val shows = repository.getPopularTvShows(page)) {
                is Result.Success -> {
                    val data = shows.data

                    fetchPopularShows.addAll(data.results.toPopularListModel())
                    _popularShowsList.value = fetchPopularShows

                    _hasMore.value = data.page < data.totalPages

                    loaded()
                    hasInternet()
                }
                is Result.Error -> {
                    loaded()
                    hasInternet()
                }
                is Result.Internet -> {
                    loaded()
                    noInternet()
                }
            }
        }
    }
}