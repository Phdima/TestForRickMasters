package com.example.testforrickmasters.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testforrickmasters.presentation.componentsForScreens.AgeSexView
import com.example.testforrickmasters.presentation.componentsForScreens.MostViewersView
import com.example.testforrickmasters.presentation.componentsForScreens.VisitGraphView

@Composable
fun MainScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface)
            .padding(top = 21.dp),

    ) {

        LazyColumn() {
            item {
                Text(
                    "Статистика",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                    maxLines = 1
                )
            }
            item { VisitGraphView()}

            item { MostViewersView() }

            item { AgeSexView() }


        }

    }
}