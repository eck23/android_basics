package com.example.loginscreen.api

import com.example.loginscreen.models.CarList
import com.example.loginscreen.models.LoginRequest
import com.example.loginscreen.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {


    @POST("/auth/login")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @GET("api/vehicles/getallmanufacturers?format=json")
    suspend fun getCarNames(): CarList

    @GET("api/vehicles/getallmanufacturers?format=json")
    suspend fun getCarNamesByPage(
        @Query("page") page: Int
    ): CarList


}