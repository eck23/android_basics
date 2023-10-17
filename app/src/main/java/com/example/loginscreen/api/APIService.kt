package com.example.loginscreen.api

import com.example.loginscreen.models.CarList
import com.example.loginscreen.models.LoginRequest
import com.example.loginscreen.models.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {



        @POST("/auth/login")
        suspend fun login( @Body credentials: LoginRequest):LoginResponse

        @GET("api/vehicles/getallmanufacturers?format=json")
        suspend fun getCarNames(): CarList




}