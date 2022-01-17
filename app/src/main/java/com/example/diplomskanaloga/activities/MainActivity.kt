package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.VolleyError
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.interfaces.VolleyResponse
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.services.EmployeeRestService
import com.example.diplomskanaloga.utils.ChartUtils
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    lateinit var employeeRestService: EmployeeRestService
    lateinit var userData: Employee

    // elements
    lateinit var welcomeTextView: TextView

    // util
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        welcomeTextView = findViewById(R.id.welcome_text)
        employeeRestService = EmployeeRestService()
        isUserLoggedIn()
    }

    fun isUserLoggedIn() {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        // if user isn't logged in forward to auth activity else dashboard
        sharedPreferences.getString("token", "")?.let { Log.e("log", it) }
        if(!sharedPreferences.contains(getString(R.string.preference_token))) {
            val intent = Intent(this, AuthenticationActivity::class.java).apply {
            }
            startActivity(intent)
        }else {
            employeeRestService.getUserData(this, object: VolleyResponse {
                override fun onSuccess(response: Any?) {
                    userData = gson.fromJson(response.toString(), Employee::class.java)
                    welcomeTextView.append(", "+userData.name)
                    Log.w("Response", userData.toString())
                }

                override fun onError(error: VolleyError?) {
                    if (error != null) {
                        Log.e("Response Error", error.networkResponse.toString())
                    }
                }
            })
        }
    }

    fun barTest() {
        val entries:MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 20f))
        entries.add(BarEntry(2f, 50f))
        entries.add(BarEntry(3f, 60f))
        entries.add(BarEntry(4f, 10f))
        entries.add(BarEntry(5f, 70f))

        val barData = BarDataSet(entries, null)
        val data = BarData(barData)
        data.barWidth = 0.9f
        val chart: BarChart = ChartUtils.setBarChartDefaults( findViewById<BarChart>(R.id.weekly_bar_chart2))
        chart.data = data
        // set text size of bar values
        chart.data.setValueTextSize(14f)

        chart.invalidate()
    }
}