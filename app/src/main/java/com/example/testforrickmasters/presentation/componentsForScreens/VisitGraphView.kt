package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testforrickmasters.presentation.viewModels.TimeVisitorViewModel

@Composable
fun VisitGraphView(viewModel: TimeVisitorViewModel = hiltViewModel()) {
    val chartData by viewModel.chartData.collectAsState()
    val selectedPeriod by viewModel.selectedPeriod.collectAsState()


    Column(
        modifier = Modifier
            .width(344.dp)
            .height(222.dp)
            .padding(start = 16.dp, top = 12.dp)
    ) {
        Text(
            text = "Посетители",
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            maxLines = 1
        )
        LazyRow {
            item {
                PeriodSelectorForGraph(
                    selectedPeriod = selectedPeriod,
                    onPeriodSelected = viewModel::selectPeriod
                )
            }
        }
        Box(
            modifier = Modifier
                .width(344.dp)
                .height(186.dp)
                .background(color = Color.White, shape = RoundedCornerShape(20.dp))
        ) {
            Graph(
                dataPoints = chartData.points.map { it.second },
                dates = chartData.points.map { it.first }
            )
        }
    }

}

@Composable
fun Graph(dataPoints: List<Int>, dates: List<String>) {


    Box(modifier = Modifier.height(200.dp).padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphHeight = size.height
            val graphWidth = size.width
            val maxY = dataPoints.maxOrNull()?.toFloat() ?: 1f
            val stepX = graphWidth / (dataPoints.size - 1)

            // Отрисовка линии
            val path = Path().apply {
                dataPoints.forEachIndexed { index, value ->
                    val x = stepX * index
                    val y = graphHeight - (value / maxY * graphHeight)
                    if (index == 0) moveTo(x, y) else lineTo(x, y)
                }
            }
            drawPath(path, Color(0xFFF99963), style = Stroke(8f))

            // Отрисовка точек
            dataPoints.forEachIndexed { index, value ->
                val x = stepX * index
                val y = graphHeight - (value / maxY * graphHeight)
                drawCircle(Color(0xFFF99963), 12f, Offset(x, y))
            }
        }

        // Метки дат
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            dates.forEach { date ->
                Text(date, fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}