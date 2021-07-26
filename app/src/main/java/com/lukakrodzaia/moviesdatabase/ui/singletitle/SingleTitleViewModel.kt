package com.lukakrodzaia.moviesdatabase.ui.singletitle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SimilarListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SingleTitleModel
import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.DefaultTvRepository
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.utils.toSimilarListModel
import com.lukakrodzaia.moviesdatabase.utils.toSingleTitleModel
import kotlinx.coroutines.launch

class SingleTitleViewModel(private val repository: DefaultTvRepository) : BaseViewModel() {
    private val _singleTitleData = MutableLiveData<SingleTitleModel>()
    val singleTitleData: LiveData<SingleTitleModel> = _singleTitleData

    private val fetchSimilarShows: MutableList<SimilarListModel> = ArrayList()
    private val _similarTitlesList = MutableLiveData<List<SimilarListModel>>()
    val similarTitlesList: LiveData<List<SimilarListModel>> = _similarTitlesList

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    fun getSingleTitle(id: Int) {
        viewModelScope.launch {
            isLoading(true)
            when (val title = repository.getTitleDetails(id)) {
                is Result.Success -> {
                    val data = title.data

                    _singleTitleData.value = data.toSingleTitleModel()

                    isLoading(false)
                    isInternet(true)
                }
                is Result.Error -> {
                    isLoading(false)
                    isInternet(true)
                }
                is Result.Internet -> {
                    isLoading(false)
                    isInternet(false)
                }
            }
        }
    }

    fun getSimilarTitles(id: Int, page: Int) {
        viewModelScope.launch {
            isLoading(true)
            when (val similar = repository.getSimilarTvShows(id, page)) {
                is Result.Success -> {
                    val data = similar.data

                    fetchSimilarShows.addAll(data.results.toSimilarListModel())
                    _similarTitlesList.value = fetchSimilarShows
                    _hasMore.value = data.page < data.totalPages

                    isLoading(false)
                    isInternet(true)
                }
                is Result.Error -> {
                    isLoading(false)
                    isInternet(true)
                }
                is Result.Internet -> {
                    isLoading(false)
                    isInternet(false)
                }
            }
        }
    }
}