package com.pec_acm.moviedroid.mainpage.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.App
import com.pec_acm.moviedroid.R
import com.pec_acm.moviedroid.model.MovieResult
import com.pec_acm.moviedroid.model.TvResult
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel: ViewModel() {

    val topMovies : MutableLiveData<List<MovieResult>> = MutableLiveData()
    val topTVShows: MutableLiveData<List<TvResult>> = MutableLiveData()
    val popularMovies: MutableLiveData<List<MovieResult>> = MutableLiveData()
    val popularTVShows: MutableLiveData<List<TvResult>> = MutableLiveData()
    val nowPlayingMovies : MutableLiveData<List<MovieResult>> = MutableLiveData()
    val upcomingMovies : MutableLiveData<List<MovieResult>> = MutableLiveData()
    fun getTopMovies(){
        viewModelScope.launch {
            try {
                topMovies.value = ApiInstance.api.getTopMovies().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getTopTVShows(){
        viewModelScope.launch {
            try {
                topTVShows.value = ApiInstance.api.getTopTvShows().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getPopularMovies(){
        viewModelScope.launch {
            try {
                popularMovies.value = ApiInstance.api.getPopularMovies().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getPopularTVShows(){
        viewModelScope.launch {
            try {
                popularTVShows.value = ApiInstance.api.getPopularTvShows().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getNowPlayingMovies(){
        viewModelScope.launch {
            try {
                nowPlayingMovies.value = ApiInstance.api.getNowPlayingMovies().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getUpcomingMovies(){
        viewModelScope.launch {
            try {
                upcomingMovies.value = ApiInstance.api.getUpcomingMovies().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, App.appContext?.getString(R.string.toast_exception, e.toString()), Toast.LENGTH_SHORT).show()
            }
        }
    }
}