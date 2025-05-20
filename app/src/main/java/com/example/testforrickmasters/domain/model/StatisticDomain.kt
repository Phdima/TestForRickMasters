package com.example.testforrickmasters.domain.model

data class StatisticDomain(
    val userId: Int,
    val type: String,
    val dates: List<Long>
)