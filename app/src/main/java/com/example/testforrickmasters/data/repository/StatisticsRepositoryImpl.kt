package com.example.testforrickmasters.data.repository

import com.example.testforrickmasters.data.api.StatisticsApi
import com.example.testforrickmasters.data.dataObjects.statistic.StatisticApiData
import com.example.testforrickmasters.domain.model.StatisticDomain
import com.example.testforrickmasters.domain.repsitory.StatisticsRepository


class StatisticsRepositoryImpl(
    private val api: StatisticsApi
) : StatisticsRepository {
    override suspend fun getStatistics(): List<StatisticDomain> {
        val response = api.getStatistics()
        return response.statistics.map { dataObj ->
            StatisticDomain(
                userId = dataObj.userId,
                type = dataObj.type,
                dates = dataObj.dates
            )
        }
    }
}