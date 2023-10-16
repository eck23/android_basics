package com.example.loginscreen.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {



        @POST("/auth/login")
        suspend fun login(@Body credentials: RequestBody): Response<ResponseBody>


}