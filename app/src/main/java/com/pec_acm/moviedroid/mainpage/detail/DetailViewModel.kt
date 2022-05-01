package com.pec_acm.moviedroid.mainpage.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.model.MovieDetail
import com.pec_acm.moviedroid.model.TVDetail
import kotlinx.coroutines.launch

class DetailViewModel: ViewModel() {

    val movieDetailList : MutableLiveData<MovieDetail> = MutableLiveData()
    val tvDetailList : MutableLiveData<TVDetail> = MutableLiveData()

    fun getMovieDetail(id:Int){
       viewModelScope.launch {
           movieDetailList.value = ApiInstance.api.movieDetailByID(movie_id = id).body()
       }
    }

    fun getTVShowDetail(id:Int){
        viewModelScope.launch {
            tvDetailList.value = ApiInstance.api.tvShowByID(tv_id = id).body()
        }
    }
}
