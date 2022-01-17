package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
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
        barchartTest(R.id.weekly_bar_chart)
        lineChartTest()
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

    fun barchartTest(id: Int) {
        val entries:MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 30f))
        entries.add(BarEntry(1f, 120f))
        entries.add(BarEntry(2f, 50f))
        entries.add(BarEntry(3f, 60f))
        entries.add(BarEntry(4f, 10f))
        entries.add(BarEntry(5f, 70f))
        val barData = BarDataSet(entries, null)
        val data = BarData(barData)
        data.barWidth = 0.9f
        val chart: BarChart = ChartUtils.setBarChartDefaults( findViewById<BarChart>(id))
        chart.data = data
        chart.animateXY(1000,1000)
        // set text size of bar values
        chart.data.setValueTextSize(14f)
        chart.animate()
    }

    fun horizontalBarChartText(id: Int) {
        val entries:MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 12f))
        entries.add(BarEntry(1f, 32f))
        entries.add(BarEntry(2f, 4f))
        entries.add(BarEntry(3f, 15f))
        entries.add(BarEntry(4f, 14f))
        entries.add(BarEntry(5f, 71f))
        entries.add(BarEntry(6f, 71f))
        entries.add(BarEntry(7f, 10f))
        entries.add(BarEntry(8f, 174f))
        entries.add(BarEntry(9f, 11f))
        entries.add(BarEntry(10f, 11f))
        entries.add(BarEntry(11f, 11f))
        entries.reverse()
        val barData = BarDataSet(entries, null)
        barData.setDrawValues(true)
        barData.valueTextSize = 14f
        barData.isHighlightEnabled = true
        val data = BarData(barData)
        data.setDrawValues(true)
        data.setValueTextColor(Color.BLACK)
        data.barWidth = 0.9f
        val chart: BarChart = ChartUtils.setHorizontalBarChartDefault( findViewById<HorizontalBarChart>(id))

        chart.data = data
        chart.setMaxVisibleValueCount(12)
        chart.xAxis.setLabelCount(entries.size, false)

        chart.setDrawValueAboveBar(false)
        chart.data.setValueTextColor(Color.BLACK)
        chart.xAxis.labelCount = entries.size
        // set text size of bar values
        chart.axisLeft.axisMaximum = 250f
        chart.axisLeft.axisMinimum = 0f
        chart.animateXY(1000,1000)
        chart.setDrawValueAboveBar(true)
        chart.invalidate()
        chart.refreshDrawableState()
    }

    fun lineChartTest() {

        val entries:MutableList<Entry> = ArrayList()
        entries.add( Entry(0f, 44f))
        entries.add( Entry(1f, 11f))
        entries.add( Entry(2f, 22f))
        entries.add( Entry(3f, 34f))
        entries.add( Entry(4f, 47f))
        val lineDataSet = LineDataSet(entries, "test")
        lineDataSet.lineWidth = 4f
        lineDataSet.color = Color.CYAN
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawHighlightIndicators(true)
        lineDataSet.isHighlightEnabled = true
        lineDataSet.highLightColor = Color.CYAN
        lineDataSet.valueTextColor = Color.DKGRAY
        lineDataSet.mode = LineDataSet.Mode.STEPPED
        val chart = findViewById<LineChart>(R.id.yearly_overview_chart)
        chart.data = LineData(lineDataSet)
        chart.invalidate()
    }
}