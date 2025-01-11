package com.devspacecinenow.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.common.data.RetrofitClient
import com.devspacecinenow.common.model.MovieDTO
import com.devspacecinenow.detail.data.DetailService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel(
    private val detailService: DetailService
) : ViewModel() {

    private val _uiMovie = MutableStateFlow<MovieDTO?>(null)
    val uiMovie: StateFlow<MovieDTO?> = _uiMovie

    fun fetchMovieDetail(movieId: String){
        if (_uiMovie.value == null) {
        detailService.getMovieById(movieId).enqueue(
            object : Callback<MovieDTO> {
                override fun onResponse(call: Call<MovieDTO>, response: Response<MovieDTO>) {
                    if (response.isSuccessful) {
                        _uiMovie.value = response.body()
                    } else {
                        Log.d("MovieDetailScreen", "Request Error :: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<MovieDTO>, t: Throwable) {
                    Log.d("MovieDetailScreen", "Network Error :: ${t.message}")
                }

            }
        ) }
    }

    fun cleanMovieId(){
        viewModelScope.launch {
            delay(1000)
            _uiMovie.value = null
        }

    }

    companion object {
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return MovieDetailViewModel(detailService) as T
            }

        }
    }
}


