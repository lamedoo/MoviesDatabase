package com.lukakrodzaia.moviesdatabase.repositories.tvrepository

import com.lukakrodzaia.moviesdatabase.network.Result
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetSimilarTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTvShowSearchResponse

interface DefaultTvRepository {
    suspend fun getPopularTvShows(page: Int): Result<GetPopularTvShowsResponse>
    suspend fun getTitleDetails(id: Int): Result<GetTitleDetailsResponse>
    suspend fun getSimilarTvShows(id: Int, page: Int): Result<GetSimilarTvShowsResponse>
    suspend fun getTvShowSearch(query: String, page: Int): Result<GetTvShowSearchResponse>
}