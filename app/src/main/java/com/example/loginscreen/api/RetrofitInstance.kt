package com.example.loginscreen.api

import android.util.Log
import com.example.loginscreen.models.LoginResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.*
import java.lang.Exception

class RetrofitInstance {

    companion object {

       fun requestLogin(username: String, password: String): String? {

            var loginResponse : String ?=null


            try{

                // Create Retrofit
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://dummyjson.com")
                    .build()

                // Create Service
                val service = retrofit.create(APIService::class.java)

                // Create JSON using JSONObject
                val jsonObject = JSONObject()
                jsonObject.put("username", username)
                jsonObject.put("password", password)


                // Convert JSONObject to String
                val jsonObjectString = jsonObject.toString()


                val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

                CoroutineScope(Dispatchers.IO).launch {
                    // Do the POST request and get response
                    val response = service.login(requestBody)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to pretty JSON using GSON library
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            val loginResponse = gson.toJson(
                                JsonParser.parseString(
                                    response.body()
                                        ?.string()
                                )
                            )




                            Log.d("JSON RESPONSE :", loginResponse)


                        } else {

                            Log.e("RETROFIT_ERROR", response.code().toString())

                        }
                    }
                }
            }catch (e:Exception){

            }

            return loginResponse
        }


    }
}