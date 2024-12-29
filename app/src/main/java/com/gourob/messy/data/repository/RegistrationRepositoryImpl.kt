package com.gourob.messy.data.repository

import com.google.gson.Gson
import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.data.model.RegistrationResponse
import com.gourob.messy.data.network.RegistrationApi
import com.gourob.messy.domain.model.RegistrationError
import com.gourob.messy.domain.model.Result
import com.gourob.messy.domain.repository.RegistrationRepository
import retrofit2.HttpException
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val api: RegistrationApi
) : RegistrationRepository {
    override suspend fun registerUser(request: RegistrationRequest): Result<RegistrationResponse, RegistrationError> {
        return try {
            val response = api.registerUser(request)
            Result.Success(response)
        } catch (e: HttpException) {
            val errorJson =  e.response()?.errorBody()?.string()
            val regError = Gson().fromJson(errorJson, RegistrationError::class.java)
            Result.Error(RegistrationError(regError.message))
        } catch (e: Exception) {
            Result.Error(RegistrationError(e.message))
        }
    }
}