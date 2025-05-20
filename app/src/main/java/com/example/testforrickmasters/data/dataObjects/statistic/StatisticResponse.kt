package com.example.testforrickmasters.data.dataObjects.statistic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatisticsResponse(
    @SerialName("statistics")
    val statistics: List<StatisticApiData>
)