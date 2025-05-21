package com.example.testforrickmasters.domain.useCases

import android.text.format.DateUtils
import com.example.testforrickmasters.domain.model.AgeGroupData
import com.example.testforrickmasters.domain.model.StatisticDomain
import com.example.testforrickmasters.domain.model.TimePeriod
import com.example.testforrickmasters.domain.repsitory.StatisticsRepository
import com.example.testforrickmasters.domain.repsitory.UserRepository
import jakarta.inject.Inject
import java.util.Calendar

class GetAgeDistributionUseCase @Inject constructor(
    private val statRepo: StatisticsRepository,
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(period: TimePeriod): List<AgeGroupData> {
        val now = System.currentTimeMillis()

        val filteredStats = when (period) {
            TimePeriod.TODAY -> filterByDays(statRepo.getStatistics(), now, 1)
            TimePeriod.WEEK -> filterByDays(statRepo.getStatistics(), now, 7)
            TimePeriod.MONTH -> filterByDays(statRepo.getStatistics(), now, 30)
            TimePeriod.ALL_TIME -> statRepo.getStatistics()
        }


        // Получаем уникальные userId из статистики
        val uniqueUserIds = filteredStats.map { it.userId }.distinct()

        // Получаем данные пользователей только для уникальных ID
        val users = userRepo.getUsers()
            .filter { it.id in uniqueUserIds }
            .associateBy { it.id }

        val ageGroups = mapOf(
            "18-21" to (18..21),
            "22-25" to (22..25),
            "26-30" to (26..30),
            "31-35" to (31..35),
            "36-40" to (36..40),
            "40-50" to (40..50),
            ">50" to (51..Int.MAX_VALUE)
        )

        return ageGroups.map { (rangeName, range) ->

            val usersInGroup = users.values.filter { user ->
                user.age in range
            }.filter { user ->
                filteredStats.any { stat -> stat.userId == user.id }
            }

            val maleCount = usersInGroup.count {
                it.sex.equals("M", ignoreCase = true)
            }

            val femaleCount = usersInGroup.count {
                it.sex.equals("W", ignoreCase = true)
            }

            AgeGroupData(
                ageRange = rangeName,
                maleCount = maleCount,
                femaleCount = femaleCount
            )
        }

    }

    private fun filterByDays(
        stats: List<StatisticDomain>,
        currentTime: Long,
        days: Int
    ): List<StatisticDomain> {
        val calendar = Calendar.getInstance().apply { timeInMillis = currentTime }
        calendar.add(Calendar.DAY_OF_YEAR, -days)
        val startTime = calendar.timeInMillis

        return stats.filter { stat ->
            stat.type == "view" &&
                    stat.dates.any { date -> date >= startTime }
        }
    }
}
