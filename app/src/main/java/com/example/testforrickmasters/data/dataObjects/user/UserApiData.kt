package com.example.testforrickmasters.data.dataObjects.user

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val sex: String,
    val username: String,
    val isOnline: Boolean,
    val age: Int,
    val files: List<File>
)

@Serializable
data class File(
    val id: Int,
    val url: String,
    val type: String
)