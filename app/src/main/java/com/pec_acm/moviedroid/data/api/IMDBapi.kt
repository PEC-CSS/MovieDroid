package com.pec_acm.moviedroid.data.api

import com.pec_acm.moviedroid.data.api.ApiInstance.API_KEY
import retrofit2.Response
import retrofit2.http.GET

interface IMDBapi {

    @GET("/Top250Movies/$API_KEY")
    suspend fun getTop250Movies() : Response<List<Movie>>
}