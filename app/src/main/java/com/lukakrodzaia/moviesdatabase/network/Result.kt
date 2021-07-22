package com.lukakrodzaia.moviesdatabase.network

import java.io.IOException

sealed class Result<out T: Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Internet(val exception: String) : Result<Nothing>()
    data class Error(val exception: String) : Result<Nothing>()
}

class InternetConnection(message: String): IOException(message)