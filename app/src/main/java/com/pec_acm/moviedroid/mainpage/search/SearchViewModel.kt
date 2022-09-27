package com.pec_acm.moviedroid.mainpage.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pec_acm.moviedroid.data.api.ApiInstance
import com.pec_acm.moviedroid.firebase.User
import com.pec_acm.moviedroid.model.MovieResult
import com.pec_acm.moviedroid.model.TvResult
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var databaseReference = Firebase.database.reference
    private var userReference = databaseReference.child("Users")
    val user : MutableLiveData<User> = MutableLiveData()
    val movieSearchList : MutableLiveData<List<MovieResult>> = MutableLiveData()
    val tvShowSearchList : MutableLiveData<List<TvResult>> = MutableLiveData()
    val searchResult : MutableLiveData<SearchResult> = MutableLiveData()

    fun searchMovie(query : String)
    {
        viewModelScope.launch {
            searchResult.value = SearchResult.SEARCHING
            movieSearchList.value = ApiInstance.api.searchMovie(query).body()?.results

            if(movieSearchList.value?.isNotEmpty() == true){
                searchResult.value = SearchResult.FOUND
            }
            else{
                searchResult.value = SearchResult.NOT_FOUND
            }
        }
    }

    fun searchTvShow(query : String)
    {
        viewModelScope.launch {
            searchResult.value = SearchResult.SEARCHING
            tvShowSearchList.value = ApiInstance.api.searchTvShow(query).body()?.results

            if(tvShowSearchList.value?.isNotEmpty() == true){
                searchResult.value = SearchResult.FOUND
            }
            else{
                searchResult.value = SearchResult.NOT_FOUND
            }
        }
    }

    fun getUser(uid : String)
    {
        viewModelScope.launch {
            userReference.child(uid).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists())
                    {
                        user.value = snapshot.getValue(User::class.java)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}