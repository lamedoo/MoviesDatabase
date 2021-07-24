package com.lukakrodzaia.moviesdatabase.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse


fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisibleOrGone(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

fun List<GetPopularTvShowsResponse.Result>.toPopularListModel(): List<PopularListModel> {
    return map {
        PopularListModel(
            id = it.id,
            name = it.name,
            poster = "${EndPoints.IMAGE_BASE_URL}${it.posterPath}",
            score = it.voteAverage
        )
    }
}

fun ImageView.setImage(image: String?) {
    Glide.with(context)
        .load(image ?: R.drawable.no_poster)
        .placeholder(R.drawable.no_poster)
        .into(this)
}