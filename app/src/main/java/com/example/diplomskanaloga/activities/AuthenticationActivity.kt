package com.example.diplomskanaloga.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.services.EmployeeRestService

class AuthenticationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        val employeeRestService = EmployeeRestService()

        onRegisterButtonClick(employeeRestService)

    }

    fun onRegisterButtonClick(employeeRestService: EmployeeRestService) {
        val registerBtn = findViewById<Button>(R.id.buttonRegister)
        registerBtn.setOnClickListener {
            employeeRestService.registerNewUser(this)
        }
    }
}