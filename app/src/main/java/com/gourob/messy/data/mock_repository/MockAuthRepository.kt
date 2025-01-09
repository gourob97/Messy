package com.gourob.messy.data.mock_repository

import com.gourob.messy.data.model.LoginRequest
import com.gourob.messy.data.model.LoginResponse
import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.data.model.RegistrationResponse
import com.gourob.messy.domain.model.LoginError
import com.gourob.messy.domain.model.RegistrationError
import com.gourob.messy.domain.model.Result
import com.gourob.messy.domain.repository.AuthRepository

class MockAuthRepository : AuthRepository {
    override suspend fun registerUser(request: RegistrationRequest): Result<RegistrationResponse, RegistrationError> {
        val response = RegistrationResponse(message = "Registered")
        return Result.Success(response)
    }

    override suspend fun loginUser(request: LoginRequest): Result<LoginResponse, LoginError> {
        val response = LoginResponse(
            isSuccess = true,
            token = "dummy_token"
        )
        return Result.Success(response)
    }
}