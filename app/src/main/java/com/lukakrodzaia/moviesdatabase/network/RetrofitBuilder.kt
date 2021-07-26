package com.lukakrodzaia.moviesdatabase.network

import com.lukakrodzaia.moviesdatabase.network.interceptors.NetworkConnectionInterceptor
import com.lukakrodzaia.moviesdatabase.network.interceptors.QueryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(private val networkConnectionInterceptor: NetworkConnectionInterceptor, private val queryInterceptor: QueryInterceptor) {
    private var retrofitInstance: Retrofit? = null

    fun getRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(networkConnectionInterceptor)
            .addInterceptor(queryInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        if (retrofitInstance == null) {
            retrofitInstance = Retrofit.Builder()
                .baseUrl(EndPoints.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitInstance!!
    }
}