package com.pec_acm.moviedroid.model

data class TvListResponse(
    val page: Int,
    val results: List<TvResult>,
    val total_pages: Int,
    val total_results: Int
)