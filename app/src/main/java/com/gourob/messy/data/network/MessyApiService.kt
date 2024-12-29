package com.gourob.messy.data.network

import com.gourob.messy.data.model.RegistrationRequest
import com.gourob.messy.data.model.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface RegistrationApi {
    @POST("register")
    suspend fun registerUser(@Body request: RegistrationRequest): RegistrationResponse
}
