package com.example.testforrickmasters.domain.model

data class ChartData(
    val points: List<Pair<String, Int>>,
    val totalVisitors: Int
)