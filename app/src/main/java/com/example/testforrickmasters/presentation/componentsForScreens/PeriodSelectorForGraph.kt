package com.example.testforrickmasters.presentation.componentsForScreens

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.testforrickmasters.domain.model.TimeByPeriod
import com.example.testforrickmasters.domain.model.TimePeriod

@Composable
fun PeriodSelectorForGraph(
    onPeriodSelected: (TimeByPeriod) -> Unit,
    selectedPeriod: TimeByPeriod,
) {
    TimeByPeriod.entries.forEach { period ->
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
val TimeByPeriod.displayName: String
    get() = when (this) {
        TimeByPeriod.BYDAYS -> "По дням"
        TimeByPeriod.BYWEEKS -> "По неделям"
        TimeByPeriod.BYMONTHS -> "По месяцам"
    }