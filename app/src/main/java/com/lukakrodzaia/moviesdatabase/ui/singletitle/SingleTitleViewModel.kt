package com.lukakrodzaia.moviesdatabase.ui.singletitle

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lukakrodzaia.moviesdatabase.datamodels.SingleTitleModel
import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.utils.toSingleTitleModel
import kotlinx.coroutines.launch

class SingleTitleViewModel : BaseViewModel() {
    private val _singleTitleData = MutableLiveData<SingleTitleModel>()
    val singleTitleData: LiveData<SingleTitleModel> = _singleTitleData

    fun getSingleTitle(id: Int) {
        viewModelScope.launch {
            when (val title = repository.getTitleDetails(id)) {
                is Result.Success -> {
                    val data = title.data

                    _singleTitleData.value = data.toSingleTitleModel()

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