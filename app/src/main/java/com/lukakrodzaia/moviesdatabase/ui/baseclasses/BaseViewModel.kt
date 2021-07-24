package com.lukakrodzaia.moviesdatabase.ui.baseclasses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.DefaultTvRepository
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.TvRepository
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject

open class BaseViewModel: ViewModel(), KoinComponent {
    private val tvRepository: TvRepository = get()
    protected val repository: DefaultTvRepository = tvRepository

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isInternet = MutableLiveData(true)
    val isInternet: LiveData<Boolean> = _isInternet

    fun loading() {
        _isLoading.value = true
    }

    fun loaded() {
        _isLoading.value = false
    }

    fun hasInternet() {
        _isInternet.value = true
    }

    fun noInternet() {
        _isInternet.value = false
    }
}