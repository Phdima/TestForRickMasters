package com.example.testforrickmasters.domain.useCases

import com.example.testforrickmasters.domain.repsitory.StatisticsRepository
import jakarta.inject.Inject

class GetStatisticsUseCase @Inject constructor(
    private val repo: StatisticsRepository
){
    suspend operator fun invoke() = repo.getStatistics()
}