package com.example.loginscreen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginscreen.api.APIService
import com.example.loginscreen.api.RetrofitInstance
import com.example.loginscreen.api.values.LOGIN_BASE_URL
import com.example.loginscreen.models.LoginRequest
import com.example.loginscreen.models.LoginResponse
import kotlinx.coroutines.launch

class LoginViewModel :ViewModel(){

    var loginResponse: MutableLiveData<LoginResponse> = MutableLiveData()

    fun loginUser(username:String, password:String){

        viewModelScope.launch {

            try {
                val retrofit = RetrofitInstance.getInstance(LOGIN_BASE_URL)

                val service = retrofit.create(APIService::class.java)

                var response = service.login(LoginRequest(username,password))

                loginResponse.value=response

            }catch (e:Exception){

                //handle error
            }



        }
    }

}