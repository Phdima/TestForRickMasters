package com.example.testforrickmasters.domain.useCases

import android.util.Log
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.repsitory.StatisticsRepository
import com.example.testforrickmasters.domain.repsitory.UserRepository
import jakarta.inject.Inject

class GetProfileVisitorsUseCase @Inject constructor(
    private val statRepo: StatisticsRepository,
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(): List<UserDomain> {

        val sortedStats = statRepo.getStatistics()
            .filter { it.type == "view" }
            .sortedByDescending { it.dates.size }

        val orderedUserIds = sortedStats.map { it.userId }.distinct()

        val allUsers = userRepo.getUsers().associateBy { it.id }

        return orderedUserIds.mapNotNull { userId ->
            allUsers[userId]
        }
    }
}