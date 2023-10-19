package com.example.loginscreen


import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.loginscreen.api.APIService
import com.example.loginscreen.api.RetrofitInstance
import com.example.loginscreen.api.values.LOGIN_BASE_URL

import com.example.loginscreen.models.LoginRequest
import com.example.loginscreen.models.LoginResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


import java.lang.Exception



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




        submitButton.setOnClickListener {

            var username = usernameTextField.text.toString()
            var password = passwordTextField.text.toString()

            if (switch) {

                validateCredentials(username = username, password = password)

            } else {


                showToast(R.string.signup_unavailable.toString())

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


        //Validate Credentials using Retrofit post request

        val retrofit = RetrofitInstance.getInstance(LOGIN_BASE_URL)

        // Create Service
        val service = retrofit.create(APIService::class.java)


        val request= LoginRequest(username,password)




        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            withContext(Dispatchers.Main) {

                try{
                    val response = service.login(request)
//                    Log.d("API RESPONSE :", response.toString())
                     storeData(response)
                     gotoHomeScreen(response)

                }catch (e:Exception){

//                    Log.d("API RESPONSE :",e.toString())

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


        fun gotoHomeScreen(userData: LoginResponse) {

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


        fun storeData(loginResponse: LoginResponse){

            val userDataSharedPref= getSharedPreferences("userData", MODE_PRIVATE)
            val editUserData= userDataSharedPref.edit()

            editUserData.putString("firstName",loginResponse.firstName)

            editUserData.apply()

        }


}
