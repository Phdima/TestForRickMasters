package com.example.testforrickmasters.domain.repsitory

import com.example.testforrickmasters.data.api.UserApi
import com.example.testforrickmasters.data.dataObjects.user.User
import com.example.testforrickmasters.domain.model.UserDomain

interface UserRepository {
    suspend fun getUsers() : List<UserDomain>
}