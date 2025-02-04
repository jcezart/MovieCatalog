package com.devspacecinenow.detail.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MovieDetailScreen(
    movieId: String,
    navHostController: NavHostController,
    detailViewModel: MovieDetailViewModel,
) {
    val movieDto by detailViewModel.uiMovie.collectAsState()
    detailViewModel.fetchMovieDetail(movieId)


    movieDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    detailViewModel.cleanMovieId()
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = it.title,
                    fontSize = 24.sp
                )
            }
            MovieDetailContent(it)
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun MovieDetailContent(movie: MovieDTO) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .size(400.dp)
                .clip(RoundedCornerShape(16.dp))
                .align(Alignment.CenterHorizontally)
                .background(Color.White)
                .shadow(32.dp),
            elevation = CardDefaults.cardElevation(8.dp)

        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                model = movie.posterFullPath,
                contentDescription = "${movie.title} Poster Image"
            )
        }
        Spacer(modifier = Modifier.size(38.dp))

        Row {
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "Runtime"
            )

            Text(
                text = movie.runtime + " Min"
            )

            Spacer(modifier = Modifier.size(10.dp))

            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Release Date"
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = movie.releaseDate
            )

            Spacer(modifier = Modifier.size(10.dp))

            Icon(
                imageVector = Icons.Filled.Info,
                contentDescription = "Genre"
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = movie.genres?.firstOrNull()?.name ?: "Desconhecido"
            )

            Spacer(modifier = Modifier.size(10.dp))

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Ratio"
            )

            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = String.format("%.1f", movie.ratio)
            )

        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = movie.overview,
            fontSize = 16.sp
        )

    }
}

//@Composable
//private fun MovieProviderContent(movie2: MovieDTO2) {
//    Text(
//        text = movie2.providerName ?: "Provedor não disponível"
//    )
//}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview() {

}