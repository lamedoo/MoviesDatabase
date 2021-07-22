package com.lukakrodzaia.moviesdatabase.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import com.lukakrodzaia.moviesdatabase.network.InternetConnection
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isConnected()) {
            throw InternetConnection("შეამოწმეთ ინტერნეტთან კავშირი")
        } else {
            return chain.proceed(chain.request())
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return (netInfo != null && netInfo.isConnected)
    }
}