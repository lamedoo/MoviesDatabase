package com.lukakrodzaia.moviesdatabase.utils

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lukakrodzaia.moviesdatabase.R
import com.lukakrodzaia.moviesdatabase.datamodels.PopularListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SearchListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SimilarListModel
import com.lukakrodzaia.moviesdatabase.datamodels.SingleTitleModel
import com.lukakrodzaia.moviesdatabase.network.EndPoints
import com.lukakrodzaia.moviesdatabase.network.models.response.GetPopularTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetSimilarTvShowsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTitleDetailsResponse
import com.lukakrodzaia.moviesdatabase.network.models.response.GetTvShowSearchResponse


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

fun View.showKeyboard() {
    this.requestFocus()
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
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

fun List<GetSimilarTvShowsResponse.Result>.toSimilarListModel(): List<SimilarListModel> {
    return map {
        SimilarListModel(
            id = it.id,
            name = it.name,
            poster = if (it.posterPath != null) "${EndPoints.IMAGE_BASE_URL}${it.posterPath}" else "",
            score = it.voteAverage
        )
    }
}

fun List<GetTvShowSearchResponse.Result>.toSearchListModel(): List<SearchListModel> {
    return map {
        SearchListModel(
            id = it.id,
            name = it.name,
            poster = if (it.posterPath != null) "${EndPoints.IMAGE_BASE_URL}${it.posterPath}" else "",
            date = it.firstAirDate ?: "N/A",
            score = it.voteAverage
        )
    }
}

fun GetTitleDetailsResponse.toSingleTitleModel(): SingleTitleModel {
    val genres = ArrayList<String>()
    this.genres.forEach { genres.add(it.name) }

    return SingleTitleModel(
        id = id,
        name = name,
        poster = "${EndPoints.IMAGE_BASE_URL}${posterPath}",
        cover = "${EndPoints.IMAGE_BASE_URL}${backdropPath}",
        overview = overview,
        rating = voteAverage.toString(),
        date = firstAirDate ?: "N/A",
        length = "${episodeRunTime[0]} წთ.",
        genres = TextUtils.join(", ", genres),
        seasons = numberOfSeasons.toString(),
        episodes = numberOfEpisodes.toString()
    )
}

fun ImageView.setImage(image: String?) {
    Glide.with(context)
        .load(image ?: R.drawable.no_poster)
        .placeholder(R.drawable.no_poster)
        .into(this)
}