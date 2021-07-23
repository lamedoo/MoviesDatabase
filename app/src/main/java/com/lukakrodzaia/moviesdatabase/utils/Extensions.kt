package com.lukakrodzaia.moviesdatabase.utils

import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse


fun List<GetPopularTvShowsResponse.Result>.toPopularListModel(): List<PopularListModel> {
    return map {
        PopularListModel(
            id = it.id,
            name = it.name,
            poster = it.posterPath,
            score = it.voteAverage
        )
    }
}