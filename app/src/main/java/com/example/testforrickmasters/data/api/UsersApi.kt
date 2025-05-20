package com.example.testforrickmasters.data.api

import com.example.testforrickmasters.data.dataObjects.user.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import jakarta.inject.Inject

interface UserApi {
    suspend fun getUsers(): UsersResponse
}

class UserApiImpl @Inject constructor(
    private val client: HttpClient
) : UserApi {
    override suspend fun getUsers(): UsersResponse {
        return client.get("http://test.rikmasters.ru/api/users/").body()
    }
}