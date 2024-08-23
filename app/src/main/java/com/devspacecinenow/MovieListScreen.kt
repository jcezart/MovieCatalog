package com.devspacecinenow

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieListScreen(navController: NavHostController) {

    //Armazena a lista dos filmes, o mutableStateOf serve para criar uma lista com um estado
    //mutável. Sempre que a variável for alterada, ela será recomposta automaticamente
    //O remember serve para manter o valor da variável no ciclo de vida da UI, se houver mudanças
    //de estado, ela será preservada.
    var nowPlayingMovies by remember { mutableStateOf<List<MovieDTO>>(emptyList()) }
    var topRatedMovies by remember { mutableStateOf<List<MovieDTO>>(emptyList()) }
    var upComingMovies by remember { mutableStateOf<List<MovieDTO>>(emptyList()) }
    var popularMovies by remember { mutableStateOf<List<MovieDTO>>(emptyList()) }

    //Criação da implementação da interface ApiService para fazer chamadas para a API
    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

    //Preparação para a chamada da API para buscar os filmes
    val callNowPlaying = apiService.getNowPlayingMovies()

    //Execução da chamada da API utilizando o método enqueue, ou seja, a requisição será feita
    //em segundo plano, afim de não bloquear a thread principal, que é responsável por atualizar
    //a interface do usuário
    callNowPlaying.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>, response: Response<MovieResponse>
        ) {
            //Quando a API chega, esse método é chamado, se a resposta for bem sucedida
            //vai extrair a lista de filmes (results). Se a lista não for nula, irá atualizar
            //a variável nowPlayingMovies
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    nowPlayingMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Erro :: ${response.errorBody()}")
            }
        }

        //Se a requisição falhar, irá mostrar o log no logcat
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    /////////////////////////////////////////////////////////////////////////////

    val callTopRatedMovies = apiService.getTopRatedMovies()
    callTopRatedMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    topRatedMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Erro :: ${response.errorBody()}")
            }
        }
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    /////////////////////////////////////////////////////////////////////////////

    val callUpcomingMovies = apiService.getUpcomingMovies()
    callUpcomingMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    upComingMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Erro :: ${response.errorBody()}")
            }
        }
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    /////////////////////////////////////////////////////////////////////////////

    val callPopularMovies = apiService.getPopularMovies()
    callPopularMovies.enqueue(object : Callback<MovieResponse> {
        override fun onResponse(
            call: Call<MovieResponse>,
            response: Response<MovieResponse>
        ) {
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    popularMovies = movies
                }
            } else {
                Log.d("MainActivity", "Request Erro :: ${response.errorBody()}")
            }
        }
        override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    MovieListContent(
        nowPlayingMovies = nowPlayingMovies,
        topRatedMovies = topRatedMovies,
        upComingMovies = upComingMovies,
        popularMovies = popularMovies
    ){ itemClicked ->
        navController.navigate(route = "movieDetail/${itemClicked.id}")

    }

}

@Composable
private fun MovieListContent(
    nowPlayingMovies: List<MovieDTO>,
    topRatedMovies: List<MovieDTO>,
    upComingMovies: List<MovieDTO>,
    popularMovies: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "MovieCatalog"
        )

        MovieSession(
            label = "Now Playing",
            movieList = nowPlayingMovies,
            onClick = onClick
        )

        MovieSession(
            label = "Top Rated",
            movieList = topRatedMovies,
            onClick = onClick
        )

        MovieSession(
            label = "Upcoming",
            movieList = upComingMovies,
            onClick = onClick
        )

        MovieSession(
            label = "Popular",
            movieList = popularMovies,
            onClick = onClick
        )

    }
}

@Composable
private fun MovieSession(
    label: String,
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            text = label
        )
        Spacer(modifier = Modifier.size(8.dp))
        MovieList(movieList = movieList, onClick = onClick)

    }
}

@Composable
private fun MovieList(
    movieList: List<MovieDTO>,
    onClick: (MovieDTO) -> Unit
) {
    LazyRow {
        items(movieList) {
            MovieItem(
                movieDTO = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun MovieItem(
    movieDTO: MovieDTO,
    onClick: (MovieDTO) -> Unit
) {

    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieDTO)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDTO.posterFullPath,
            contentDescription = "${movieDTO.title} Poster Image",
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontWeight = FontWeight.SemiBold,
            text = movieDTO.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movieDTO.overview,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
