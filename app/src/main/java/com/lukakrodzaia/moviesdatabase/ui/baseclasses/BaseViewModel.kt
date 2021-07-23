package com.lukakrodzaia.moviesdatabase.ui.baseclasses

import androidx.lifecycle.ViewModel
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.DefaultTvRepository
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.TvRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel: ViewModel(), KoinComponent {
    private val tvRepository: TvRepository by inject()
    protected val repository: DefaultTvRepository = tvRepository
}