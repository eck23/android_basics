package com.example.loginscreen.models


data class UserData(var userName: String, var userId: String, var password: String)


var accountsMap = mutableMapOf<String, UserData>()
