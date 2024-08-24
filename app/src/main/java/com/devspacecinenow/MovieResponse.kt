package com.devspacecinenow

//MovieResponse é uma classe que representa a estrutura geral da resposta da API
data class MovieResponse(
    val results: List<MovieDTO>
)

data class Genre(
    val id: Int,
    val name: String
)
