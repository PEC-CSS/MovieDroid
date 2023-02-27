package com.pec_acm.moviedroid.model

data class PersonListResponse(
        val page: Int,
        val results: List<PersonResult>,
        val total_pages: Int,
        val total_results: Int
)