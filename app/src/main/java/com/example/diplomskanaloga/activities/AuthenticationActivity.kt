package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.android.volley.VolleyError
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.interfaces.VolleyResponse
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
        employeeRestService.login(this, loginCridentials, object: VolleyResponse {
            override fun onSuccess(response: Any?) {
                openMainActivity()
            }
            override fun onError(error: VolleyError?) {
            }
        })
    }

    fun openMainActivity() {
        val intent = Intent(this, NavigationActivity::class.java).apply {
        }
        startActivity(intent)
    }
}