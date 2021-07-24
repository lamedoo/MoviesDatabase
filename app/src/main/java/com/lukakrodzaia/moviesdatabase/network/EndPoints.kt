package com.lukakrodzaia.moviesdatabase.network

object EndPoints {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/original"

    const val API_KEY = "abfd4643bc864c611510b31d490439f2"

    const val GET_POPULAR_TV_SHOWS = "tv/popular"
    const val GET_TITLE_DETAILS = "tv/{tv_id}"
    const val GET_SIMILAR_TV_SHOWS = "tv/{tv_id}/similar"

    const val ERROR_CODE_401 = "401"
    const val ERROR_CODE_404 = "404"
    const val ERROR_CODE_UNKNOWN = "unknown error"
}