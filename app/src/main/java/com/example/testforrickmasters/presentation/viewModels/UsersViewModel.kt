package com.example.testforrickmasters.presentation.viewModels

import androidx.lifecycle.ViewModel
import com.example.testforrickmasters.domain.useCases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

}