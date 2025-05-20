package com.example.testforrickmasters.data.repository

import com.example.testforrickmasters.data.api.UserApi
import com.example.testforrickmasters.data.dataObjects.user.File
import com.example.testforrickmasters.data.dataObjects.user.User
import com.example.testforrickmasters.domain.model.UserDomain
import com.example.testforrickmasters.domain.repsitory.UserRepository

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {
    override suspend fun getUsers(): List<UserDomain> {
        val response = api.getUsers()
        return response.users.map { dataObj ->
            UserDomain(
               id = dataObj.id,
                sex = dataObj.sex,
                username = dataObj.username,
                isOnline = dataObj.isOnline,
                age = dataObj.age,
                avatarUrl = dataObj.files.map { file ->
                    File(
                        id = file.id,
                        url = file.url,
                        type = file.type
                    )
                }.toString()
            )
        }
    }
}