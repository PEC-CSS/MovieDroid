package com.pec_acm.moviedroid.firebase

data class User(
    var uid : String="",
    var name : String? = null,
    var imageUrl : String? = null,
    var email : String? = null,
    var userList: MutableList<ListItem> = mutableListOf()
)