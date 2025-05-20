package com.example.testforrickmasters.data.api

import com.example.testforrickmasters.data.dataObjects.statistic.StatisticsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

interface StatisticsApi {
    suspend fun getStatistics(): StatisticsResponse
}

class StatisticsApiImpl @Inject constructor(
    private val client: HttpClient
) : StatisticsApi {
    override suspend fun getStatistics(): StatisticsResponse {
        return client.get("http://test.rikmasters.ru/api/statistics/").body()
    }
}