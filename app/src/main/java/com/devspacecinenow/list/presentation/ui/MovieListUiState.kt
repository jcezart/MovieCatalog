package com.devspacecinenow.list.presentation.ui

import com.devspacecinenow.common.model.Genre
import com.google.gson.annotations.SerializedName



data class MovieListUiState(
    val list : List<MovieUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong",
)

data class MovieUiData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
    @SerializedName("release_date") val releaseDate: String,
    val runtime: String?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("vote_average") val ratio: Float
)
