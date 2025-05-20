package com.example.testforrickmasters.domain.repsitory

import com.example.testforrickmasters.data.dataObjects.statistic.StatisticApiData
import com.example.testforrickmasters.domain.model.StatisticDomain


interface StatisticsRepository {
    suspend fun getStatistics(): List<StatisticDomain>
}