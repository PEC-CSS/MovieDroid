package com.pec_acm.moviedroid.model

data class MovieListResponse(
    val page: Int,
    val results: List<MovieResult>,
    val total_pages: Int,
    val total_results: Int
)