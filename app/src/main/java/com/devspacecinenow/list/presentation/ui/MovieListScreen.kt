package com.devspacecinenow.list.presentation.ui


import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.list.presentation.MovieListViewModel


@Composable
fun MovieListScreen(navController: NavHostController,
                    viewModel: MovieListViewModel) {

    //Armazena a lista dos filmes, o mutableStateOf serve para criar uma lista com um estado
    //mutável. Sempre que a variável for alterada, ela será recomposta automaticamente
    //O remember serve para manter o valor da variável no ciclo de vida da UI, se houver mudanças
    //de estado, ela será preservada.
    val nowPlayingMovies by viewModel.uiNowPlaying.collectAsState()
    val topRatedMovies by viewModel.uiTopRated.collectAsState()
    val upComingMovies by viewModel.uiUpcoming.collectAsState()
    val popularMovies by viewModel.uiPopular.collectAsState()

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
    nowPlayingMovies: MovieListUiState,
    topRatedMovies: List<MovieDTO>,
    upComingMovies: List<MovieDTO>,
    popularMovies: List<MovieDTO>,
    onClick: (MovieUiData) -> Unit,
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
            movieListUiState = nowPlayingMovies,
            onClick = onClick
        )
//
//        MovieSession(
//            label = "Top Rated",
//            movieList = topRatedMovies,
//            onClick = onClick
//        )
//
//        MovieSession(
//            label = "Upcoming",
//            movieList = upComingMovies,
//            onClick = onClick
//        )
//
//        MovieSession(
//            label = "Popular",
//            movieList = popularMovies,
//            onClick = onClick
//        )

    }
}

@Composable
private fun MovieSession(
    label: String,
    movieListUiState: MovieListUiState,
    onClick: (MovieUiData) -> Unit
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

        if (movieListUiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (movieListUiState.isError) {
            Text(
                color = Color.Red,
                text = movieListUiState.errorMessage ?: "",
            )
        } else {
            MovieList(movieList = movieListUiState.list, onClick = onClick)
        }

    }
}


@Composable
private fun MovieList(
    movieList: List<MovieUiData>,
    onClick: (MovieUiData) -> Unit
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
    movieDTO: MovieUiData,
    onClick: (MovieUiData) -> Unit
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
            model = movieDTO.image,
            contentDescription = "${movieDTO.title} Poster Image",
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontWeight = FontWeight.SemiBold,
            text = movieDTO.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

    }
}
