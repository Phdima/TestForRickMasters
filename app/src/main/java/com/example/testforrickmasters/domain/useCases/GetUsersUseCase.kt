package com.example.testforrickmasters.domain.useCases

import com.example.testforrickmasters.domain.repsitory.UserRepository
import jakarta.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repo: UserRepository
) {
    suspend operator fun invoke() = repo.getUsers()
}