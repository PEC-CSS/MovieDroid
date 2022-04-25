package com.pec_acm.moviedroid.mainpage.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.pec_acm.moviedroid.firebase.ListItem
import com.pec_acm.moviedroid.firebase.User
import kotlinx.coroutines.launch
import java.text.FieldPosition

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

    fun addItem(uid : String,listItem: ListItem)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                user?.userList?.add(listItem)
                userReference.child(uid).setValue(user)
            }
        }
    }

    fun setItemStatus(uid : String,id: Int,status : Int)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                for(i in user?.userList?.indices!!)
                {
                    val listItem = user.userList[i]
                    if(listItem.id==id)
                    {
                        listItem.status=status
                        user.userList[i] = listItem
                        userReference.child(uid).setValue(user)
                        break
                    }
                }

            }
        }
    }

    fun setItemScore(uid : String,id: Int,score : Int)
    {
        viewModelScope.launch {
            userReference.child(uid).get().addOnCompleteListener {
                val user = it.result.getValue(User::class.java)
                for(i in user?.userList?.indices!!)
                {
                    val listItem = user.userList[i]
                    if(listItem.id==id)
                    {
                        listItem.personalScore=score
                        user.userList[i] = listItem
                        userReference.child(uid).setValue(user)
                        break
                    }
                }
            }
        }
    }
}