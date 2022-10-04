package com.pec_acm.moviedroid.mainpage.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.data.api.TMDBApi
import com.pec_acm.moviedroid.model.MovieDetail
import com.pec_acm.moviedroid.model.TVDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val api : TMDBApi
) : ViewModel() {

    val movieDetailList: MutableLiveData<MovieDetail> = MutableLiveData()
    val tvDetailList: MutableLiveData<TVDetail> = MutableLiveData()

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            movieDetailList.value = api.movieDetailByID(movie_id = id).body()
        }
    }

    fun getTVShowDetail(id: Int) {
        viewModelScope.launch {
            tvDetailList.value = api.tvShowByID(tv_id = id).body()
        }
    }
}
