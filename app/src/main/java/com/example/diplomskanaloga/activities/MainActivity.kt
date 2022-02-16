package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.VolleyError
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.interfaces.VolleyResponse
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.services.EmployeeRestService
import com.example.diplomskanaloga.utils.ChartUtils
import com.example.diplomskanaloga.utils.ChartValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson





class MainActivity : AppCompatActivity(), OnChartValueSelectedListener {
    lateinit var employeeRestService: EmployeeRestService
    lateinit var userData: Employee

    // util
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        welcomeTextView = findViewById(R.id.welcome_text)
        employeeRestService = EmployeeRestService()
        isUserLoggedIn()
    }

    fun isUserLoggedIn() {
        val sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        // if user isn't logged in forward to auth activity else dashboard
        if (!sharedPreferences.contains(getString(R.string.preference_token))) {
            openAuth()
        } else {
            employeeRestService.getUserData(this, object : VolleyResponse {
                override fun onSuccess(response: Any?) {
                    userData = gson.fromJson(response.toString(), Employee::class.java)
                    Log.w("Response", userData.toString())
                    openHome()
                }

                override fun onError(error: VolleyError?) {
                    if (error != null) {
                        Log.e("Response Error", error.networkResponse.toString())
                        openAuth()
                    }
                }
            })
        }
    }

    fun openAuth() {
        val intent = Intent(this, AuthenticationActivity::class.java).apply {
        }
        startActivity(intent)
    }

    fun openHome() {
        val intent = Intent(this, NavigationActivity::class.java).apply {
        }
        startActivity(intent)

    }

    fun barchartTest(id: Int) {
        val entries: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 120f))
        entries.add(BarEntry(2f, 50f))
        entries.add(BarEntry(3f, 60f))
        entries.add(BarEntry(4f, 10f))
        entries.add(BarEntry(5f, 70f))
        val barData = BarDataSet(entries, null)
        barData.color = resources.getColor(R.color.secondaryColor)
        val data = BarData(barData)
        data.barWidth = 0.9f
        val chart: BarChart = ChartUtils.setBarChartDefaults(findViewById<BarChart>(id))
        chart.data = data
        chart.animateXY(1000, 1000)
        // set text size of bar values
        chart.data.setValueTextSize(14f)

        chart.animate()
    }



    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.w("values", e.toString())
    }

    override fun onNothingSelected() {
    }
}