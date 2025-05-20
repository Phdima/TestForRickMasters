package com.example.testforrickmasters.data.dataObjects.user

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<User>
)