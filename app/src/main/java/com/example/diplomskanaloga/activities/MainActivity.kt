package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.diplomskanaloga.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun isUserLoggedIn() {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        // if user isn't logged in forward to auth activity else dashboard
        sharedPreferences.getString("token", "")?.let { Log.e("log", it) }
        if(!sharedPreferences.contains(getString(R.string.preference_token))) {
            val intent = Intent(this, AuthenticationActivity::class.java).apply {
            }
            startActivity(intent)
        } else {
            val intent = Intent(this, DashboardActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }
}