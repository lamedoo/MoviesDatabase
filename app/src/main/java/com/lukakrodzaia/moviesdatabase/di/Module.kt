package com.lukakrodzaia.moviesdatabase.di

import com.lukakrodzaia.moviesdatabase.network.api.ApiNetwork
import com.lukakrodzaia.moviesdatabase.network.RetrofitBuilder
import com.lukakrodzaia.moviesdatabase.network.interceptors.NetworkConnectionInterceptor
import org.koin.dsl.module

val viewModelModule = module {

}

val repositoryModule = module {

}

val generalModule = module {
    single { NetworkConnectionInterceptor(get()) }
    single { RetrofitBuilder(get()) }
    single { get<RetrofitBuilder>().getRetrofitInstance().create(ApiNetwork::class.java) }
}