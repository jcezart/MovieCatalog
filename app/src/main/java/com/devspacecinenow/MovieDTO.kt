package com.devspacecinenow

import com.google.gson.annotations.SerializedName


//Representação dos dados que vem da API

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String
)
