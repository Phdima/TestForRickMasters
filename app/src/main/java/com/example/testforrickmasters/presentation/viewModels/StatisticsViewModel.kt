package com.example.testforrickmasters.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.testforrickmasters.domain.useCases.GetStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val useCase: GetStatisticsUseCase
) : ViewModel() {

}