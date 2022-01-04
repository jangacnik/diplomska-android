package com.example.diplomskanaloga.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.models.LoginCridentials
import com.example.diplomskanaloga.services.EmployeeRestService

class AuthenticationActivity : AppCompatActivity() {
    lateinit var employeeRestService: EmployeeRestService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        employeeRestService = EmployeeRestService()
    }

    fun login(view: View) {
        val usernameText = findViewById<EditText>(R.id.inputEmail).text.toString()
        val passwordText = findViewById<EditText>(R.id.inputPassword).text.toString()
        val loginCridentials = LoginCridentials(usernameText, passwordText)
        employeeRestService.login(this, loginCridentials)
        val t = this.getSharedPreferences("jwt", Context.MODE_PRIVATE).getString("token", "")
    }
}