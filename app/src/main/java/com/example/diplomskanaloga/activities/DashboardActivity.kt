package com.example.diplomskanaloga.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.services.EmployeeRestService

class DashboardActivity : AppCompatActivity() {
    lateinit var employeeRestService: EmployeeRestService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        employeeRestService = EmployeeRestService()
    }
}