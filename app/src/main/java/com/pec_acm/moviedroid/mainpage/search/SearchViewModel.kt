package com.pec_acm.moviedroid.mainpage.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.model.MovieResult
import com.pec_acm.moviedroid.model.TvResult
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    val movieSearchList : MutableLiveData<List<MovieResult>> = MutableLiveData()
    val tvShowSearchList : MutableLiveData<List<TvResult>> = MutableLiveData()

    fun searchMovie(query : String)
    {
        viewModelScope.launch {
            movieSearchList.value = ApiInstance.api.searchMovie(query).body()?.results
        }
    }

    fun searchTvShow(query : String)
    {
        viewModelScope.launch {
            tvShowSearchList.value = ApiInstance.api.searchTvShow(query).body()?.results
        }
    }
}