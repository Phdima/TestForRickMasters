package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.testforrickmasters.domain.model.TimePeriod

@Composable
fun PeriodSelectorForAgesPie(
    onPeriodSelected: (TimePeriod) -> Unit,
    selectedPeriod: TimePeriod,
) {
    TimePeriod.entries.forEach { period ->
        val isSelected = period == selectedPeriod
        Button(
            onClick = { onPeriodSelected(period) },
            colors = ButtonColors(
                containerColor = if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                contentColor = Color.Black,
                disabledContainerColor = if (isSelected) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                disabledContentColor = Color.Black
            )
        ) {
            Text(text = period.displayName, fontSize = 12.sp)
        }
    }

}
val TimePeriod.displayName: String
    get() = when (this) {
        TimePeriod.TODAY -> "Сегодня"
        TimePeriod.WEEK -> "Неделя"
        TimePeriod.MONTH -> "Месяц"
        TimePeriod.ALL_TIME -> "Все время"
    }