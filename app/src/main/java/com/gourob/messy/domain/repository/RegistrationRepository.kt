package com.gourob.messy.domain.repository

import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.data.model.RegistrationResponse
import com.gourob.messy.domain.model.RegistrationError
import com.gourob.messy.domain.model.Result

interface RegistrationRepository {
    suspend fun registerUser(request: RegistrationRequest): Result<RegistrationResponse, RegistrationError>
}