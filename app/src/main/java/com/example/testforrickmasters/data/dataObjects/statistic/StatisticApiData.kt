package com.example.testforrickmasters.data.dataObjects.statistic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class StatisticApiData(
    @SerialName("user_id")
    val userId: Int,
    val type: String,
    val dates: List<Long>
)
