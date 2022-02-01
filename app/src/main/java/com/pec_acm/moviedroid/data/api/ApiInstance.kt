package com.pec_acm.moviedroid.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {
    private const val BASE_URL = "https://imdb-api.com/en/API"
    const val API_KEY = "k_79l2kc3v"

    val api : IMDBapi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IMDBapi::class.java)
    }

}