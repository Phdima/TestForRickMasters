package com.example.testforrickmasters.presentation.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.testforrickmasters.presentation.viewModels.UsersViewModel

@Composable
fun MainScreen(){
val VM : UsersViewModel = hiltViewModel()
}