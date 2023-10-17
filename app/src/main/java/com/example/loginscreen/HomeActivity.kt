package com.example.loginscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.loginscreen.models.LoginResponse
import com.example.loginscreen.models.accountsMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder

class HomeActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        var welcomeText = findViewById<TextView>(R.id.welcomeText)
//
//        var userData=intent.getSerializableExtra("userData") as LoginResponse


        welcomeText.setText("Hi "+ "Elson")




        loadFragment(AccountFragment())

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    loadFragment(AccountFragment())
                    true
                }
                R.id.message -> {
                    loadFragment(ProfileFragment())
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    true
                }

                else-> false
            }
        }


    }


    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}