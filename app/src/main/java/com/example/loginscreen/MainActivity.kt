package com.example.loginscreen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle

import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.loginscreen.api.APIService

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    var switch = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        val mainLayout = findViewById<ConstraintLayout>(R.id.main_layout)
        val submitButton = findViewById<Button>(R.id.submit_button)
        val loginSignUpSwitch = findViewById<TextView>(R.id.login_sigup_switch)
        val usernameTextField = findViewById<EditText>(R.id.usernameTextField)
        val passwordTextField = findViewById<EditText>(R.id.passwordTextField)


//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submitButton.setOnClickListener {

            var username = usernameTextField.text.toString()
            var password = passwordTextField.text.toString()

            if (switch) {

                validateCredentials(username = username, password = password)

            } else {
//
//                if(!accountsMap.containsKey(username)){
//
//                    var userData= UserData(username,"A111",password)
//                    accountsMap.set(username,userData)
//
//                    Toast.makeText(this@MainActivity, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
//                    switchLoginAndSignUp(submitButton,loginSignUpSwitch)
//
//                }
//                else{
//
//                    showToast("Account already exists")
//
//                }

                showToast("Signup currently not available")

            }
        }

        loginSignUpSwitch.setOnClickListener {

            switchLoginAndSignUp(submitButton, loginSignUpSwitch)

        }

        mainLayout.setOnClickListener {

            this.getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }

    fun validateCredentials(username: String, password: String) {

//        if(!accountsMap.containsKey(username)){
//
//            Toast.makeText(this@MainActivity, "Username doesn't exist.", Toast.LENGTH_SHORT).show()
//
//            return;
//        }
//
//        var userData= accountsMap[username]

//        if(password.equals(userData?.password)){
//
//            Toast.makeText(this@MainActivity, "Correct Credentials.", Toast.LENGTH_SHORT).show()
//
//            var intent=Intent(this, HomeActivity::class.java).apply{
//
//                putExtra("username", username)
//            }
//
//            try {
//                startActivity(intent)
//
//            } catch (e: ActivityNotFoundException) {
//
//                Toast.makeText(this@MainActivity, "Problem Navigating", Toast.LENGTH_SHORT).show()
//
//            }
//
//        }else{
//
//            Toast.makeText(this@MainActivity, "Wrong Password.", Toast.LENGTH_SHORT).show()
//
//        }


        //Validate Credentials using Retrofit post request

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
                    gotoHomeScreen(loginResponse)


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())
                    showToast("Couldn't Login")
                }
            }

        }
    }


        fun switchLoginAndSignUp(submitButton: Button, switchTextView: TextView) {

            if (switch) {

                submitButton.setText(R.string.signup)
                switchTextView.setText(R.string.login_switch)

            } else {

                submitButton.setText(R.string.login)
                switchTextView.setText(R.string.signup_switch)
            }

            switch = !switch

        }


        fun gotoHomeScreen(userData: String) {

            var intent = Intent(this, HomeActivity::class.java).apply {

                putExtra("userData", userData)
            }

            try {
                startActivity(intent)

            } catch (e: ActivityNotFoundException) {

                showToast("Problem Navigating")

            }
        }

        fun showToast(message: String) {

            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

}
