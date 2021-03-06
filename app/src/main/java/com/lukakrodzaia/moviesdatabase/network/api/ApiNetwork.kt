package com.lukakrodzaia.moviesdatabase.network.api

import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetSimilarTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTvShowSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNetwork {
    @GET(EndPoints.GET_POPULAR_TV_SHOWS)
    suspend fun getPopularTvShows(@Query("page") page: Int) : Response<GetPopularTvShowsResponse>

    @GET(EndPoints.GET_TITLE_DETAILS)
    suspend fun getTitleDetails(@Path("tv_id") tv_id: Int) : Response<GetTitleDetailsResponse>

    @GET(EndPoints.GET_SIMILAR_TV_SHOWS)
    suspend fun getSimilarTvShows(@Path("tv_id") tv_id: Int,
                                  @Query("page") page: Int) : Response<GetSimilarTvShowsResponse>

    @GET(EndPoints.GET_TV_SHOW_SEARCH)
    suspend fun getTvShowSearch(@Query("query") query: String,
                                @Query("page") page: Int) : Response<GetTvShowSearchResponse>
}