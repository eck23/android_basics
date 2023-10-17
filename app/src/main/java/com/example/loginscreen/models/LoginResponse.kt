package com.example.loginscreen.models


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse  (
    val email: String,
    val firstName: String,
    val gender: String,
    val id: Int,
    val image: String,
    val lastName: String,
    val token: String,
    val username: String
) : Serializable