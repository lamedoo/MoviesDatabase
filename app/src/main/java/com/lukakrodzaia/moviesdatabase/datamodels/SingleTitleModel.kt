package com.lukakrodzaia.moviesdatabase.datamodels

data class SingleTitleModel(
    val id: Int,
    val name: String,
    val poster: String,
    val cover: String,
    val overview: String,
    val rating: String,
    val date: String,
    val length: String
)
