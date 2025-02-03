package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.list.data.ListService
import com.devspacecinenow.list.presentation.ui.MovieListUiState
import com.devspacecinenow.list.presentation.ui.MovieUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUiState())
    val uiNowPlaying: StateFlow<MovieListUiState> = _uiNowPlaying

    private val _uiTopRated = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiTopRated: StateFlow<List<MovieDTO>> = _uiTopRated

    private val _uiPopular = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiPopular: StateFlow<List<MovieDTO>> = _uiPopular

    private val _uiUpcoming = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiUpcoming: StateFlow<List<MovieDTO>> = _uiUpcoming

    init {
        fetchNowPlayingMovies()
       // fetchTopRatedMovies()
       // fetchPopularMovies()
       // fetchUpcomingMovies()
    }

    private fun fetchNowPlayingMovies(){
        _uiNowPlaying.value = MovieListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listService.getNowPlayingMovies()
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        val movieUiDataList = movies.map { movieDTO -> MovieUiData(
                            id = movieDTO.id,
                            title = movieDTO.title,
                            overview = movieDTO.overview,
                            image = movieDTO.posterFullPath,
                            releaseDate = movieDTO.releaseDate,
                            runtime = movieDTO.runtime,
                            genres = movieDTO.genres,
                            ratio = movieDTO.ratio
                        ) }
                        _uiNowPlaying.value = MovieListUiState(list = movieUiDataList)

                    }
                } else {
                    _uiNowPlaying.value = MovieListUiState(isError = true)
                    Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
                }
            } catch (ex: Exception){
                ex.printStackTrace()
                _uiNowPlaying.value = MovieListUiState(isError = true)
            }

        }
    }

    private fun fetchTopRatedMovies(){
        //GWT
        // Given create new instance of viewmodel

        // When collect uiTopRated state flow

        // Then verify if date is as expected

        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiTopRated.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchPopularMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiPopular.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
            }
        }
    }

    private fun fetchUpcomingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getUpcomingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results
                if (movies != null) {
                    _uiUpcoming.value = movies
                }
            } else {
                Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return MovieListViewModel(listService) as T
            }
        }
    }

}