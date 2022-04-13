package com.pec_acm.moviedroid.mainpage.home

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.App
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.data.api.Movie
import com.pec_acm.moviedroid.data.api.TVShow
import com.pec_acm.moviedroid.data.api.Theaters
import com.pec_acm.moviedroid.model.MovieResult
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.coroutineContext

class HomeViewModel: ViewModel() {

    val topMovies : MutableLiveData<List<MovieResult>> = MutableLiveData()
    val top250TVShows: MutableLiveData<TVShow> = MutableLiveData()
    val mostPopularMovies: MutableLiveData<Movie> = MutableLiveData()
    val mostPopularTVShows: MutableLiveData<TVShow> = MutableLiveData()
    val inTheaters : MutableLiveData<Theaters> = MutableLiveData()
    val comingSoon : MutableLiveData<Theaters> = MutableLiveData()
    fun getTopMovies(){
        viewModelScope.launch {
            try {
                topMovies.value = com.pec_acm.moviedroid.ApiInstance.api.getTopMovies().body()?.results
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun get250TVShows(){
        viewModelScope.launch {
            try {
                top250TVShows.value = ApiInstance.api.getTop250TVShows().body()
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getMostPopularMovies(){
        viewModelScope.launch {
            try {
                mostPopularMovies.value = ApiInstance.api.getMostPopularMovies().body()
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getMostPopularTVshows(){
        viewModelScope.launch {
            try {
                mostPopularTVShows.value = ApiInstance.api.getMostPopularTVshows().body()
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getInTheaters(){
        viewModelScope.launch {
            try {
                inTheaters.value = ApiInstance.api.getInTheaters().body()
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getComingSoon(){
        viewModelScope.launch {
            try {
                comingSoon.value = ApiInstance.api.getComingSoon().body()
            }catch (e:Exception){
                Toast.makeText(App.appContext, "Exception : $e", Toast.LENGTH_SHORT).show()
            }
        }
    }
}