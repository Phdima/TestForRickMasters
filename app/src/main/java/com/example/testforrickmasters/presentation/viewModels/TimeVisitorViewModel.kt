package com.example.testforrickmasters.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforrickmasters.domain.model.ChartData
import com.example.testforrickmasters.domain.model.TimeByPeriod
import com.example.testforrickmasters.domain.model.TimePeriod
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.useCases.GetTimeVisitorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TimeVisitorViewModel @Inject constructor(
    private val getTimeVisitorUseCase: GetTimeVisitorUseCase
): ViewModel() {

    private val _chartData = MutableStateFlow(ChartData(emptyList(), 0))
    val chartData: StateFlow<ChartData> = _chartData

    private val _selectedPeriod = MutableStateFlow(TimeByPeriod.BYMONTHS)
    val selectedPeriod: StateFlow<TimeByPeriod> = _selectedPeriod

    init {
        loadData()
    }

    fun selectPeriod(period: TimeByPeriod) {
        _selectedPeriod.value = period
        loadData(period)
    }

    private fun loadData(period: TimeByPeriod = TimeByPeriod.BYMONTHS) {
        viewModelScope.launch {
            val data = getTimeVisitorUseCase(period)
            try {
                _chartData.value = getTimeVisitorUseCase(period)
                Log.d("ViewModel", "Data: ${data.points}")
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: ${e.message}")
            }
        }
    }
}