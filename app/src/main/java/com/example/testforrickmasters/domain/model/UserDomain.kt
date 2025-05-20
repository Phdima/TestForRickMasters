package com.example.testforrickmasters.domain.model

data class UserDomain(
    val id: Int,
    val sex: String,
    val username: String,
    val isOnline: Boolean,
    val age: Int,
    val avatarUrl: String?
)