package com.example.loginscreen

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        var welcomeText = findViewById<TextView>(R.id.welcomeText)



        loadFragment(HomeFragment())
        welcomeText.setText("Cars For You")

        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                    welcomeText.setText("Cars For You")
                    true
                }
                R.id.message -> {
                    loadFragment(ProfileFragment())
                    welcomeText.setText("My Profile")
                    true
                }
                R.id.settings -> {
                    loadFragment(SettingsFragment())
                    welcomeText.setText("Settings")
                    true
                }

                else-> false
            }
        }


    }


    private fun loadFragment(fragment: Fragment){

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}