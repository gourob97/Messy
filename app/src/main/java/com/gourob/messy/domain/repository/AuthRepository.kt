package com.gourob.messy.domain.repository

import com.gourob.messy.data.model.LoginRequest
import com.gourob.messy.data.model.LoginResponse
import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.data.model.RegistrationResponse
import com.gourob.messy.domain.model.LoginError
import com.gourob.messy.domain.model.RegistrationError
import com.gourob.messy.domain.model.Result

interface AuthRepository {
    suspend fun registerUser(request: RegistrationRequest): Result<RegistrationResponse, RegistrationError>

    suspend fun loginUser(request: LoginRequest): Result<LoginResponse, LoginError>
}