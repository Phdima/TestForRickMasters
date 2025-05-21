package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testforrickmasters.domain.model.AgeGroupData
import com.example.testforrickmasters.presentation.viewModels.StatisticsViewModel
import kotlin.math.roundToInt

@Composable
fun AgeSexView(
    viewModel: StatisticsViewModel = hiltViewModel()
) {

    val ageDistribution by viewModel.ageDistribution.collectAsState()
    val selectedPeriod by viewModel.selectedPeriod.collectAsState()


    Box(
        modifier = Modifier
            .width(344.dp)
            .height(610.dp)
            .padding(start = 16.dp, top = 12.dp)
    ) {
        Column {
            Text(
                text = "Пол и возраст",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 1
            )
            LazyRow {
                item {
                    CustomButton(
                        selectedPeriod = selectedPeriod,
                        onPeriodSelected = viewModel::selectPeriod
                    )
                }
            }

            Column(
                modifier = Modifier
                    .width(344.dp)
                    .height(529.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))

            ) {
                GenderPieChart(
                    ageDistribution = ageDistribution,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 18.dp)
                )

                AgeDistributionChart(
                    ageDistribution = ageDistribution,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun GenderPieChart(
    ageDistribution: List<AgeGroupData>,
    modifier: Modifier = Modifier,
    maleColor: Color = MaterialTheme.colorScheme.primary,
    femaleColor: Color = MaterialTheme.colorScheme.secondary,
    strokeWidth: Float = 50f
) {
    val maleCount = ageDistribution.sumOf { it.maleCount }
    val femaleCount = ageDistribution.sumOf { it.femaleCount }

    val total = maleCount + femaleCount
    val maleAngle = if (total > 0) 360f * maleCount / total else 0f
    val femaleAngle = if (total > 0) 360f * femaleCount / total else 0f


    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(152.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {

                drawArc(
                    color = femaleColor,
                    startAngle = -90f + maleAngle,
                    sweepAngle = femaleAngle,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Butt)
                )
                drawArc(
                    color = maleColor,
                    startAngle = -90f,
                    sweepAngle = maleAngle,
                    useCenter = false,
                    style = Stroke(strokeWidth, cap = StrokeCap.Butt)
                )
            }
        }

        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GenderLegendItem("Мужчины", maleColor, maleCount, total)
            GenderLegendItem("Женщины", femaleColor, femaleCount, total)
        }
    }
}


@Composable
private fun GenderLegendItem(
    text: String,
    color: Color,
    count: Int,
    total: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("$text: ${"%.1f".format(100f * count / total)}%", fontSize = 12.sp)
    }
}


@Composable
fun AgeDistributionChart(
    maleColor: Color = MaterialTheme.colorScheme.primary,
    femaleColor: Color = MaterialTheme.colorScheme.secondary,
    ageDistribution: List<AgeGroupData>,
    modifier: Modifier = Modifier
) {
    val totalVisitors = ageDistribution.sumOf { it.maleCount + it.femaleCount }
    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            ageDistribution.forEach { group ->
                AgeGroupRow(
                    ageGroup = group,
                    maleColor = maleColor,
                    femaleColor = femaleColor,
                    totalVisitors = totalVisitors,
                )
            }
        }
    }
}

@Composable
private fun AgeGroupRow(
    ageGroup: AgeGroupData,
    maleColor: Color,
    femaleColor: Color,
    totalVisitors: Int,
) {


    // Проценты от общего числа посетителей
    val malePercent = if (totalVisitors > 0) {
        (ageGroup.maleCount.toFloat() / totalVisitors * 100).roundToInt()
    } else 0

    val femalePercent = if (totalVisitors > 0) {
        (ageGroup.femaleCount.toFloat() / totalVisitors * 100).roundToInt()
    } else 0


    // Вес для полос (доля внутри группы + минимальное значение)
    val maleWeight = (ageGroup.maleCount.toFloat() / (totalVisitors+0.01f)).coerceAtLeast(0.01f)

    val femaleWeight = (ageGroup.femaleCount.toFloat() / (totalVisitors+0.01f)).coerceAtLeast(0.01f)


    Row(

        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = ageGroup.ageRange,
            modifier = Modifier.width(60.dp),
            fontSize = 14.sp
        )

        Column(modifier = Modifier.height(40.dp)) {

            // Полоса мужчин
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .weight(
                            maleWeight
                        )
                        .background(maleColor, RoundedCornerShape(4.dp))
                )
                Spacer(Modifier.width(4.dp))
                Text("$malePercent%", fontSize = 12.sp, modifier = Modifier.weight(1f))
            }

            // Полоса женщин
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .background(femaleColor, RoundedCornerShape(4.dp))
                        .weight(
                            femaleWeight
                        )
                )
                Spacer(Modifier.width(4.dp))
                Text("$femalePercent%", fontSize = 12.sp, modifier = Modifier.weight(1f))
            }
        }
    }
}
