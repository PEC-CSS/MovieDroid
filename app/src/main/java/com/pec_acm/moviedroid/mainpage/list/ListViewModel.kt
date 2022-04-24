package com.pec_acm.moviedroid.mainpage.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pec_acm.moviedroid.firebase.User
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private var databaseReference = Firebase.database.reference
    private var userReference = databaseReference.child("Users")
    val user : MutableLiveData<User> = MutableLiveData()

    fun getUser(uid : String)
    {
        viewModelScope.launch {
            userReference.child(uid).addValueEventListener(object : ValueEventListener{
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

    fun addUser(uid : String,name : String?,imageUrl : String?,email : String?)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val snapshot = it.result
                if(!snapshot.exists())
                {
                    userReference.child(uid).setValue(User(uid,name,imageUrl,email))
                }
            }
        }
    }
}