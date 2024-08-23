package com.devspacecinenow

import com.google.gson.annotations.SerializedName


//Representação dos dados que vem da API, cada item dentro da lista 'results' da MovieResponse é
// representado por um MovieDTO, contendo os itens individuais, como id, title, overview

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String
){
    val posterFullPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}
