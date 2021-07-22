package com.lukakrodzaia.moviesdatabase.network.api

import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetSimilarTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNetwork {
    @GET(EndPoints.GET_POPULAR_TV_SHOWS)
    suspend fun getPopularTvShows(@Query("api_key") api_key: String = EndPoints.API_KEY,
                                  @Query("page") page: Int) : Response<GetPopularTvShowsResponse>

    @GET(EndPoints.GET_TITLE_DETAILS)
    suspend fun getTitleDetails(@Path("tv_id") tv_id: Int,
                                @Query("api_key") api_key: String = EndPoints.API_KEY
    ) : Response<GetTitleDetailsResponse>

    @GET(EndPoints.GET_SIMILAR_TV_SHOWS)
    suspend fun getSimilarTvShows(@Path("tv_id") tv_id: Int,
                                  @Query("api_key") api_key: String = EndPoints.API_KEY
    ) : Response<GetSimilarTvShowsResponse>
}