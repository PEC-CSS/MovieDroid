package com.pec_acm.moviedroid.data.api

import com.pec_acm.moviedroid.model.MovieListResponse
import com.pec_acm.moviedroid.model.TvListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("movie/top_rated")
    suspend fun getTopMovies(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<MovieListResponse>

    @GET("tv/top_rated")
    suspend fun getTopTvShows(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<TvListResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<MovieListResponse>

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<TvListResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<MovieListResponse>

    @GET("tv/now_playing")
    suspend fun getNowPlayingTvShows(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<TvListResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<MovieListResponse>

    @GET("tv/upcoming")
    suspend fun getUpcomingTvShows(
        @Query("api_key")
        apiKey : String = ApiInstance.API_KEY
    ) : Response<TvListResponse>
}