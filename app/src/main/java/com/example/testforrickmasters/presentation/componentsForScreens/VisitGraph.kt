package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun VisitGraph() {

    val dataPoints = listOf(12, 21, 15, 8, 19, 7, 14) // Пример данных
    val dates = listOf("05.03", "06.03", "07.03", "08.03", "09.03", "10.03", "11.03")

    Box(
        modifier = Modifier
            .height(200.dp)
            .padding(vertical = 16.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val graphHeight = size.height
            val graphWidth = size.width
            val maxValue = dataPoints.maxOrNull() ?: 1

            // Рисуем линии графика
            val path = Path().apply {
                dataPoints.forEachIndexed { index, value ->
                    val x = (graphWidth / (dataPoints.size - 1)) * index
                    val y = graphHeight - (value.toFloat() / maxValue * graphHeight)

                    if (index == 0) moveTo(x, y) else lineTo(x, y)
                }
            }

            drawPath(
                path = path,
                color = Color.Blue,
                style = Stroke(width = 4f)
            )

            // Рисуем точки
            dataPoints.forEachIndexed { index, value ->
                val x = (graphWidth / (dataPoints.size - 1)) * index
                val y = graphHeight - (value.toFloat() / maxValue * graphHeight)

                drawCircle(
                    color = Color.Blue,
                    radius = 8f,
                    center = Offset(x, y)
                )
            }
        }
    }
}