package com.example.testforrickmasters.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforrickmasters.domain.model.AgeGroupData
import com.example.testforrickmasters.domain.model.TimePeriod
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.useCases.GetAgeDistributionUseCase
import com.example.testforrickmasters.domain.useCases.GetProfileVisitorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val getAgeDistributionUseCase: GetAgeDistributionUseCase
) : ViewModel() {

    private val _ageDistribution = MutableStateFlow<List<AgeGroupData>>(emptyList())
    private val _selectedPeriod = MutableStateFlow(TimePeriod.ALL_TIME)

    val selectedPeriod: StateFlow<TimePeriod> = _selectedPeriod
    val ageDistribution = _ageDistribution.asStateFlow()

    init {
        loadData()
    }

    fun selectPeriod(period: TimePeriod) {
        _selectedPeriod.value = period
        loadData(period)
    }

    private fun loadData(period: TimePeriod = TimePeriod.ALL_TIME) {
        viewModelScope.launch {
            try {
                _ageDistribution.value = getAgeDistributionUseCase(period)
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: ${e.message}")
            }
        }
    }
}