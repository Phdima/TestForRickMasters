package com.example.testforrickmasters.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testforrickmasters.domain.model.TimePeriod
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.useCases.GetAgeDistributionUseCase
import com.example.testforrickmasters.domain.useCases.GetProfileVisitorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class VisitorViewModel @Inject constructor(
    private val getVisitorsUseCase: GetProfileVisitorsUseCase,
) : ViewModel() {
    private val _visitors = MutableStateFlow<List<UserDomain>>(emptyList())
    val visitors = _visitors.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                _visitors.value = getVisitorsUseCase()
            } catch (e: Exception) {
                Log.e("ViewModel", "Error: ${e.message}")
            }
        }
    }


}