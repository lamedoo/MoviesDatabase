package com.lukakrodzaia.moviesdatabase.network.interceptors

import com.lukakrodzaia.moviesdatabase.network.EndPoints
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class QueryInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url: HttpUrl = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", EndPoints.API_KEY)
            .build()
        val request: Request = chain.request()
            .newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }
}