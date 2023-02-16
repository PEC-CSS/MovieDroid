package com.pec_acm.moviedroid.mainpage.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pec_acm.moviedroid.data.api.TMDBApi
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.User
import com.pec_acm.moviedroid.model.MovieCredits
import com.pec_acm.moviedroid.model.MovieDetail
import com.pec_acm.moviedroid.model.MovieTvVideo
import com.pec_acm.moviedroid.model.TVDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val api: TMDBApi
) : ViewModel() {

    val movieDetailList: MutableLiveData<MovieDetail> = MutableLiveData()
    val tvDetailList: MutableLiveData<TVDetail> = MutableLiveData()
    val movieVideoDetails: MutableLiveData<MovieTvVideo> = MutableLiveData()
    val tvVideoDetails: MutableLiveData<MovieTvVideo> = MutableLiveData()

    //movie and tv shows credits
    val movieCreditsList: MutableLiveData<MovieCredits> = MutableLiveData()
    val tvCreditsList: MutableLiveData<MovieCredits> = MutableLiveData()

    private var databaseReference = Firebase.database.reference
    private var userReference = databaseReference.child("Users")
    val user : MutableLiveData<User> = MutableLiveData()

    val isFav: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            movieDetailList.value = api.movieDetailByID(movie_id = id).body()
        }
    }

    fun getMovieCredits(id: Int) {
        viewModelScope.launch {
            movieCreditsList.value = api.movieCreditsByID(movie_id = id).body()
        }
    }

    fun getTvCredits(id: Int) {
        viewModelScope.launch {
            tvCreditsList.value = api.tvCreditsByID(tv_id = id).body()
        }
    }

    fun getTVShowDetail(id: Int) {
        viewModelScope.launch {
            tvDetailList.value = api.tvShowByID(tv_id = id).body()
        }
    }

    fun getMovieVideo(id: Int) {
        viewModelScope.launch {
            movieVideoDetails.value = api.movieVideosByID(movie_id = id).body()
        }
    }

    fun getTvVideo(id: Int) {
        viewModelScope.launch {
            tvVideoDetails.value = api.tvVideosByID(tv_id = id).body()
        }
    }

    fun addFavItem(uid : String,listItem: ListItem)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                user?.favList?.add(listItem)
                userReference.child(uid).setValue(user)
            }
        }
    }

    fun removeFavItem(uid : String,listItem: ListItem)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                user?.favList?.remove(listItem)
                userReference.child(uid).setValue(user)
            }
        }
    }

    fun setFavItem(uid : String,listItem: ListItem)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                isFav.value = user?.favList?.contains(listItem) == true
            }
        }
    }
}
