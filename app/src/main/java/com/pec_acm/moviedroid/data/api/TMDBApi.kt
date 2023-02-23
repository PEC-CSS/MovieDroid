package com.pec_acm.moviedroid.data.api

import com.pec_acm.moviedroid.data.Constants
import com.pec_acm.moviedroid.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieListResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieListResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieListResponse>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieListResponse>


    @GET("tv/top_rated")
    suspend fun getTopTvShows(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<TvListResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<TvListResponse>

    @GET("tv/now_playing")
    suspend fun getNowPlayingTvShows(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<TvListResponse>

    @GET("tv/upcoming")
    suspend fun getUpcomingTvShows(
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<TvListResponse>

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("query")
        query: String,
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<TvListResponse>

    @GET("movie/{movie_id}")
    suspend fun movieDetailByID(
        @Path("movie_id") movie_id : Int,
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieDetail>

    @GET("tv/{tv_id}")
    suspend fun tvShowByID(
        @Path("tv_id") tv_id : Int,
        @Query("api_key")
        apiKey : String = Constants.API_KEY,
    ) : Response<TVDetail>

    @GET("movie/{movie_id}/videos")
    suspend fun movieVideosByID(
        @Path("movie_id") movie_id : Int,
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieTvVideo>

    @GET("tv/{tv_id}/videos")
    suspend fun tvVideosByID(
        @Path("tv_id") tv_id : Int,
        @Query("api_key")
        apiKey : String = Constants.API_KEY
    ) : Response<MovieTvVideo>


    @GET("movie/{movie_id}/credits")
    suspend fun movieCreditsByID(
        @Path("movie_id") movie_id: Int,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ) : Response<MovieTvCredits>

    @GET("tv/{tv_id}/credits")
    suspend fun tvCreditsByID(
        @Path("tv_id") tv_id: Int,
        @Query("api_key")
        apiKey: String = Constants.API_KEY
    ) : Response<MovieTvCredits>
}