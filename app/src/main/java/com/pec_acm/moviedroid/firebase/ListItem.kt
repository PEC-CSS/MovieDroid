package com.pec_acm.moviedroid.firebase

data class ListItem(
    val id : Int,
    val name : String,
    val category : String,
    val posterUrl : String,
    val totalEpisodes : Int,
    val score : Int,
    var status : Int = 0,
    var watchedEpisodes : Int = 0,
    var personalScore : Int = 0
)