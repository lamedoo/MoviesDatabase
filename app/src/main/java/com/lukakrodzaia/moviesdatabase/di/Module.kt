package com.lukakrodzaia.moviesdatabase.di

import com.lukakrodzaia.moviesdatabase.network.api.ApiNetwork
import com.lukakrodzaia.moviesdatabase.network.RetrofitBuilder
import com.lukakrodzaia.moviesdatabase.network.interceptors.NetworkConnectionInterceptor
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.DefaultTvRepository
import com.lukakrodzaia.moviesdatabase.repositories.tvrepository.TvRepository
import com.lukakrodzaia.moviesdatabase.ui.baseclasses.BaseViewModel
import com.lukakrodzaia.moviesdatabase.ui.popular.PopularShowsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { BaseViewModel() }
    single { PopularShowsViewModel() }
}

val repositoryModule = module {
    single { TvRepository(get()) }
}

val generalModule = module {
    single { NetworkConnectionInterceptor(get()) }
    single { RetrofitBuilder(get()) }
    single { get<RetrofitBuilder>().getRetrofitInstance().create(ApiNetwork::class.java) }
}