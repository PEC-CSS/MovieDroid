package com.pec_acm.moviedroid.model

data class PersonCredits(
        val cast: List<PersonCreditCast>,
        val crew: List<PersonCreditCrew>,
        val id: Int
)