package com.example.loginscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {


    var switch = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val mainLayout=findViewById<ConstraintLayout>(R.id.main_layout)
        val submitButton= findViewById<Button>(R.id.submit_button)
        val loginSignUpSwitch= findViewById<TextView>(R.id.login_sigup_switch)
        val usernameTextField= findViewById<EditText>(R.id.usernameTextField)
        val passwordTextField= findViewById<EditText>(R.id.passwordTextField)


//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        submitButton.setOnClickListener{

                var username=usernameTextField.text.toString()
                var password=passwordTextField.text.toString()

               if(switch){

                   validateCredentials(username = username, password = password )

               }else{

                   if(!accountsMap.containsKey(username)){

                       var userData=UserData(username,"A111",password)
                       accountsMap.set(username,userData)

                       Toast.makeText(this@MainActivity, "Successfully Signed Up", Toast.LENGTH_SHORT).show()
                       switchLoginAndSignUp(submitButton,loginSignUpSwitch)

                   }
                   else{

                       Toast.makeText(this@MainActivity, "Account already exists", Toast.LENGTH_SHORT).show()

                   }

               }
        }

        loginSignUpSwitch.setOnClickListener {

            switchLoginAndSignUp(submitButton,loginSignUpSwitch)

        }

        mainLayout.setOnClickListener{

            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }

    fun validateCredentials(username: String, password: String){

        if(!accountsMap.containsKey(username)){

            Toast.makeText(this@MainActivity, "Username doesn't exist.", Toast.LENGTH_SHORT).show()

            return;
        }

        var userData= accountsMap.get(username)

        if(password.equals(userData?.password)){

            Toast.makeText(this@MainActivity, "Correct Credentials.", Toast.LENGTH_SHORT).show()

            var intent = Intent(this,HomeActivity::class.java)
            intent.putExtra("username",username)

            startActivity(intent)


        }else{

            Toast.makeText(this@MainActivity, "Wrong Password.", Toast.LENGTH_SHORT).show()

        }
    }

    fun switchLoginAndSignUp(submitButton: Button, switchTextView: TextView){

        if(switch){

            submitButton.setText(R.string.signup)
            switchTextView.setText(R.string.login_switch)

        }else{

            submitButton.setText(R.string.login)
            switchTextView.setText(R.string.signup_switch)
        }

        switch=!switch

    }


}