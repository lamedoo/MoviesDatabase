package com.lukakrodzaia.moviesdatabase.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SearchListModel
import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.DefaultTvRepository
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.utils.toSearchListModel
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: DefaultTvRepository) : BaseViewModel() {
    private val fetchSearch: MutableList<SearchListModel> = ArrayList()
    private val _searchList = MutableLiveData<List<SearchListModel>>()
    val searchList: LiveData<List<SearchListModel>> = _searchList

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    fun getTvShowSearch(query: String, page: Int) {
        viewModelScope.launch {
            loading()
            when (val search = repository.getTvShowSearch(query, page)) {
                is Result.Success -> {
                    val data = search.data

                    fetchSearch.addAll(data.results.toSearchListModel())
                    _searchList.value = fetchSearch

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

    fun clearSearchList() {
        fetchSearch.clear()
        _searchList.value = fetchSearch
    }
}