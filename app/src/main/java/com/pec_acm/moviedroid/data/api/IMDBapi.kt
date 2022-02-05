package com.pec_acm.moviedroid.data.api

import com.pec_acm.moviedroid.data.api.ApiInstance.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface IMDBapi {

    @GET("Top250Movies/$API_KEY")
    suspend fun getTop250Movies() : Response<Movie>

    @GET("Top250TVs/$API_KEY")
    suspend fun getTop250TVShows() :Response<TVShow>

    @GET("MostPopularMovies/$API_KEY")
    suspend fun getMostPopularMovies() :Response<Movie>

    @GET("MostPopularTVs/$API_KEY")
    suspend fun getMostPopularTVshows() :Response<TVShow>

    @GET("InTheaters/$API_KEY")
    suspend fun getInTheaters() : Response<Theaters>

    @GET("ComingSoon/$API_KEY")
    suspend fun getComingSoon() : Response<Theaters>
}