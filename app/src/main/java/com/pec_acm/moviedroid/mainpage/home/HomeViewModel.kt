package com.pec_acm.moviedroid.mainpage.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.data.api.Movie
import com.pec_acm.moviedroid.data.api.TVShow
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class HomeViewModel: ViewModel() {

    val top250Movies : MutableLiveData<Movie> = MutableLiveData()
    val top250TVShows: MutableLiveData<TVShow> = MutableLiveData()
    fun get250Movies(){
        viewModelScope.launch {
            top250Movies.value = ApiInstance.api.getTop250Movies().body()
        }
    }

    fun get250TVShows(){
        viewModelScope.launch {
            top250TVShows.value = ApiInstance.api.getTop250TVShows().body()
        }
    }
}