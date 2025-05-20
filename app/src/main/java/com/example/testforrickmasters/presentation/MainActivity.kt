package com.example.testforrickmasters.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.testforrickmasters.presentation.screens.MainScreen
import com.example.testforrickmasters.ui.theme.TestForRIckMastersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestForRIckMastersTheme {
                MainScreen()
            }
        }
    }
}

