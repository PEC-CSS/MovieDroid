package com.pec_acm.moviedroid.model

data class MovieTvCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)