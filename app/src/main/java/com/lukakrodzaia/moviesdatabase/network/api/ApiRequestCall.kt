package com.lukakrodzaia.moviesdatabase.network.api

import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.InternetConnection
import com.lukakrodzaia.moviesdatabase.network.Result
import retrofit2.Response

abstract class ApiRequestCall {
    suspend fun <T: Any> apiCall(call: suspend () -> Response<T>): Result<T> {
        return try {
            val response = call.invoke()
            var error = ""
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                error = when (response.code()) {
                    401 -> {
                        EndPoints.ERROR_CODE_401
                    }
                    404 -> {
                        EndPoints.ERROR_CODE_404
                    }
                    else -> {
                        EndPoints.ERROR_CODE_UNKNOWN
                    }
                }
                Result.Error(error)
            }
        } catch (e: Exception) {
            when (e) {
                is InternetConnection -> {
                    Result.Internet(e.toString())
                }
                else -> {
                    Result.Error(e.toString())
                }
            }
        }
    }
}