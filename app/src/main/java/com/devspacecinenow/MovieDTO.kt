package com.devspacecinenow

import com.google.gson.annotations.SerializedName


//Representação dos dados que vem da API, cada item dentro da lista 'results' da MovieResponse é
// representado por um MovieDTO, contendo os itens individuais, como id, title, overview

data class MovieDTO(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("release_date") val releaseDate: String,
    val runtime: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("vote_average") val ratio: Float
) {
    val posterFullPath: String
        get() = "https://image.tmdb.org/t/p/w500$posterPath"
}

data class MovieDTO2(
    val id: Int,
    @SerializedName("provider_name") val providerName: String,
    @SerializedName("logo_path") val logoProviderPath: String

) {
    val logoProviderFullPath: String
        get() = "https://image.tmdb.org/t/p/original$logoProviderPath"
}

