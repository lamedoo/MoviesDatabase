package com.lukakrodzaia.moviesdatabase.di

import com.lukakrodzaia.moviesdatabase.network.api.ApiNetwork
import com.lukakrodzaia.moviesdatabase.network.RetrofitBuilder
import com.lukakrodzaia.moviesdatabase.network.interceptors.NetworkConnectionInterceptor
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.TvRepository
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.ui.popularshows.PopularShowsViewModel
import com.lukakrodzaia.moviesdatabase.ui.singletitle.SingleTitleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { PopularShowsViewModel() }
    viewModel { SingleTitleViewModel() }
}

val repositoryModule = module {
    single { TvRepository(get()) }
}

val generalModule = module {
    single { NetworkConnectionInterceptor(get()) }
    single { RetrofitBuilder(get()) }
    single { get<RetrofitBuilder>().getRetrofitInstance().create(ApiNetwork::class.java) }
}