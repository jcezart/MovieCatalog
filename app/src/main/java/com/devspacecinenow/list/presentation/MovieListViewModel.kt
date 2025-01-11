package com.devspacecinenow.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.common.model.MovieResponse
import com.devspacecinenow.list.data.ListService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel(
    private val listService: ListService
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiNowPlaying: StateFlow<List<MovieDTO>> = _uiNowPlaying

    private val _uiTopRated = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiTopRated: StateFlow<List<MovieDTO>> = _uiTopRated

    private val _uiPopular = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiPopular: StateFlow<List<MovieDTO>> = _uiPopular

    private val _uiUpcoming = MutableStateFlow<List<MovieDTO>>(emptyList())
    val uiUpcoming: StateFlow<List<MovieDTO>> = _uiUpcoming

    init {
        fetchNowPlayingMovies()
        fetchTopRatedMovies()
        fetchPopularMovies()
        fetchUpcomingMovies()
    }

    private fun fetchNowPlayingMovies(){
        listService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiNowPlaying.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
                }
            }
            //Se a requisição falhar, irá mostrar o log no logcat
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchTopRatedMovies() {
        listService.getTopRatedMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiTopRated.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
                }
            }
            //Se a requisição falhar, irá mostrar o log no logcat
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchPopularMovies(){
        listService.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiPopular.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
                }
            }
            //Se a requisição falhar, irá mostrar o log no logcat
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }
        })
    }

    private fun fetchUpcomingMovies(){
        listService.getUpcomingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: Call<MovieResponse>, response: Response<MovieResponse>
            ) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (movies != null) {
                        _uiUpcoming.value = movies
                    }
                } else {
                    Log.d("MovieListViewModel", "Request Erro :: ${response.errorBody()}")
                }
            }
            //Se a requisição falhar, irá mostrar o log no logcat
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("MovieListViewModel", "Network Error :: ${t.message}")
            }
        })
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