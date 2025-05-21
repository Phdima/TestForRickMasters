package com.example.testforrickmasters.domain.useCases

import android.util.Log
import com.example.testforrickmasters.domain.model.AgeGroupData
import com.example.testforrickmasters.domain.model.ChartData
import com.example.testforrickmasters.domain.model.StatisticDomain
import com.example.testforrickmasters.domain.model.TimeByPeriod
import com.example.testforrickmasters.domain.model.TimePeriod
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.repsitory.StatisticsRepository
import com.example.testforrickmasters.domain.repsitory.UserRepository
import jakarta.inject.Inject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class GetTimeVisitorUseCase @Inject constructor(
    private val statRepo: StatisticsRepository,
    private val userRepo: UserRepository
) {
    suspend operator fun invoke(period: TimeByPeriod): ChartData {
        val now = System.currentTimeMillis()
        val stats = when (period) {
            TimeByPeriod.BYDAYS -> filterByDays(statRepo.getStatistics(), now, 7)
            TimeByPeriod.BYWEEKS -> filterByWeeks(statRepo.getStatistics(), now, 4)
            TimeByPeriod.BYMONTHS -> filterByMonths(statRepo.getStatistics(), now, 6)
        }

        val dateFormatter = when (period) {
            TimeByPeriod.BYDAYS -> SimpleDateFormat("dd.MM", Locale.getDefault())
            TimeByPeriod.BYWEEKS -> SimpleDateFormat("YYYY-'W'ww", Locale.getDefault())
            TimeByPeriod.BYMONTHS -> SimpleDateFormat("MM/yyyy", Locale.getDefault())
        }

        val visitsByDate = stats
            .flatMap { stat -> stat.dates }
            .map { dateNumber -> parseCustomDate(dateNumber) }
            .groupBy { dateMillis -> dateFormatter.format(Date(dateMillis)) }
            .mapValues { (_, dates) -> dates.size }

        val sortedPoints = visitsByDate.entries
            .sortedBy { (dateStr, _) ->
                SimpleDateFormat(dateFormatter.toPattern()).parse(dateStr)?.time ?: 0
            }
            .map { (date, count) -> date to count }

        return ChartData(
            points = sortedPoints,
            totalVisitors = visitsByDate.values.sum()
        )
    }

    private fun filterByDays(
        stats: List<StatisticDomain>,
        currentTime: Long,
        days: Int
    ): List<StatisticDomain> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
            add(Calendar.DAY_OF_YEAR, -days)
        }
        val startTime = calendar.timeInMillis

        return stats.filter { stat ->
            stat.type == "view" &&
                    stat.dates.any { dateNumber ->
                        parseCustomDate(dateNumber) >= startTime
                    }
        }
    }

    private fun filterByWeeks(
        stats: List<StatisticDomain>,
        currentTime: Long,
        weeks: Int
    ): List<StatisticDomain> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
            add(Calendar.WEEK_OF_YEAR, -weeks)
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }
        val startTime = calendar.timeInMillis

        return stats.filter { stat ->
            stat.type == "view" &&
                    stat.dates.any { dateNumber ->
                        parseCustomDate(dateNumber) >= startTime
                    }
        }
    }

    private fun filterByMonths(
        stats: List<StatisticDomain>,
        currentTime: Long,
        months: Int
    ): List<StatisticDomain> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = currentTime
            add(Calendar.MONTH, -months)
            set(Calendar.DAY_OF_MONTH, 1)
        }
        val startTime = calendar.timeInMillis

        return stats.filter { stat ->
            stat.type == "view" &&
                    stat.dates.any { dateNumber ->
                        parseCustomDate(dateNumber) >= startTime
                    }
        }
    }

    private fun parseCustomDate(dateNumber: Long): Long {
        val dateStr = dateNumber.toString().padStart(7, '0')
        val day = dateStr.take(2).toInt()
        val month = dateStr.drop(2).take(2).toInt() - 1 // Месяцы 0-11
        val year = dateStr.takeLast(4).toInt()

        return Calendar.getInstance().apply {
            set(year, month, day, 0, 0, 0)
        }.timeInMillis
    }

}