package com.gourob.messy.data.model

data class LoginRequest(
    val userName: String,
    val password: String
)

data class LoginResponse(
    val isSuccess: Boolean,
    val token: String
)