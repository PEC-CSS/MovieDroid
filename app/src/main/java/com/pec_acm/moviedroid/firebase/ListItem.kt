package com.pec_acm.moviedroid.firebase

import com.pec_acm.moviedroid.model.MovieDetail
import com.pec_acm.moviedroid.model.MovieResult
import com.pec_acm.moviedroid.model.TVDetail
import com.pec_acm.moviedroid.model.TvResult

data class ListItem(
    var id : Int=0,
    val name : String="",
    val category : String="",
    val posterUrl : String="",
    val score : Double= 0.0,
    var personalScore : Int = 0,
    var status : Int = 0,
    var watchedEpisodes : Int = 0
){
    companion object{
        private const val POSTER_URL = "https://image.tmdb.org/t/p/w600_and_h900_bestv2"

        fun MovieResult.toListItem() : ListItem
        {
            return ListItem(
                id,
                title,
                "movie",
                POSTER_URL+poster_path,
                vote_average
            )
        }

        fun MovieResult.toListItem(status: Int) : ListItem
        {
            val listItem = toListItem()
            listItem.status = status
            return listItem
        }

        fun TvResult.toListItem() : ListItem
        {
            return ListItem(
                id,
                name,
                "tv",
                POSTER_URL+poster_path,
                vote_average
            )
        }

        fun TvResult.toListItem(status: Int) : ListItem
        {
            val listItem = toListItem()
            listItem.status = status
            return listItem
        }

        fun MovieDetail.toListItem() : ListItem
        {
            return ListItem(
                    id,
                    title,
                    "movie",
                    POSTER_URL+poster_path,
                    vote_average
            )
        }

        fun TVDetail.toListItem() : ListItem
        {
            return ListItem(
                    id,
                    name,
                    "tv",
                    POSTER_URL+poster_path,
                    vote_average
            )
        }
    }
}