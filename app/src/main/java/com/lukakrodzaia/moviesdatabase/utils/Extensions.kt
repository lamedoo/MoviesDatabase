package com.lukakrodzaia.moviesdatabase.utils

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SingleTitleModel
import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse


fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setVisibleOrGone(visibility: Boolean) {
    this.visibility = if (visibility) View.VISIBLE else View.GONE
}

inline fun <T : Fragment> T.applyBundle(block: Bundle.() -> Unit): T {
    val bundle = Bundle()
    bundle.block()
    arguments = bundle
    return this
}

fun List<GetPopularTvShowsResponse.Result>.toPopularListModel(): List<PopularListModel> {
    return map {
        PopularListModel(
            id = it.id,
            name = it.name,
            poster = if (it.posterPath != null) "${EndPoints.IMAGE_BASE_URL}${it.posterPath}" else "",
            score = it.voteAverage
        )
    }
}

fun GetTitleDetailsResponse.toSingleTitleModel(): SingleTitleModel {
    return SingleTitleModel(
        id = id,
        name = name,
        poster = "${EndPoints.IMAGE_BASE_URL}${posterPath}",
        cover = "${EndPoints.IMAGE_BASE_URL}${backdropPath}",
        overview = overview,
        rating = voteAverage.toString(),
        date = firstAirDate,
        length = "${episodeRunTime[0]} წთ."
    )
}

fun ImageView.setImage(image: String?) {
    Glide.with(context)
        .load(image ?: R.drawable.no_poster)
        .placeholder(R.drawable.no_poster)
        .into(this)
}