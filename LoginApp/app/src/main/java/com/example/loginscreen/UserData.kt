package com.example.loginscreen

data class UserData(var userName:String,var userId:String,var password:String)


var accountsMap= mutableMapOf<String,UserData>()