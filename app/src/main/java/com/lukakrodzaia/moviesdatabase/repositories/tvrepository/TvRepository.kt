package com.lukakrodzaia.moviesdatabase.repositories.tvrepository

import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.network.api.ApiNetwork
import com.lukakrodzaia.moviesdatabase.network.api.ApiRequestCall
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetSimilarTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTvShowSearchResponse

class TvRepository(private val apiNetwork: ApiNetwork): ApiRequestCall(), DefaultTvRepository {
    override suspend fun getPopularTvShows(page: Int): Result<GetPopularTvShowsResponse> {
        return apiCall { apiNetwork.getPopularTvShows(page) }
    }

    override suspend fun getTitleDetails(id: Int): Result<GetTitleDetailsResponse> {
        return apiCall { apiNetwork.getTitleDetails(id) }
    }

    override suspend fun getSimilarTvShows(id: Int, page: Int): Result<GetSimilarTvShowsResponse> {
        return apiCall { apiNetwork.getSimilarTvShows(id, page) }
    }

    override suspend fun getTvShowSearch(query: String, page: Int): Result<GetTvShowSearchResponse> {
        return apiCall { apiNetwork.getTvShowSearch(query, page) }
    }
}