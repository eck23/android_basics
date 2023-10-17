package com.example.loginscreen


import android.app.ProgressDialog
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
import com.example.loginscreen.models.LoginRequest
import com.example.loginscreen.models.LoginResponse

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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


        //Validate Credentials using Retrofit post request

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)


        val request= LoginRequest(username,password)


        var progress = ProgressDialog(this);
        progress.setMessage("Loading");
        progress.setCancelable(true); // disable dismiss by tapping outside of the dialog



        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            withContext(Dispatchers.Main) {
                progress.show()

                try{
                    val response = service.login(request)
                    Log.d("API RESPONSE :", response.toString())
                    gotoHomeScreen(response)

                }catch (e:Exception){

                    Log.d("API RESPONSE :",e.toString())

                }
                progress.dismiss()
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


}
