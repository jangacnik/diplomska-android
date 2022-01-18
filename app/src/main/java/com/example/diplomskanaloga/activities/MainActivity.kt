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
    // elements
    lateinit var welcomeTextView: TextView

    // util
    val gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        welcomeTextView = findViewById(R.id.welcome_text)
        employeeRestService = EmployeeRestService()
        barchartTest(R.id.weekly_bar_chart)
        horizontalBarChartText(R.id.yearly_overview_chart)
        isUserLoggedIn()
    }

    fun isUserLoggedIn() {
        val sharedPreferences =
            this.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        // if user isn't logged in forward to auth activity else dashboard
        if (!sharedPreferences.contains(getString(R.string.preference_token))) {
            val intent = Intent(this, AuthenticationActivity::class.java).apply {
            }
            startActivity(intent)
        } else {
            employeeRestService.getUserData(this, object : VolleyResponse {
                override fun onSuccess(response: Any?) {
                    userData = gson.fromJson(response.toString(), Employee::class.java)
                    welcomeTextView.append(", " + userData.name)
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

    fun horizontalBarChartText(id: Int) {
        val entries: MutableList<BarEntry> = ArrayList()
        entries.add(BarEntry(0f, 168f))
        entries.add(BarEntry(1f, 141f))
        entries.add(BarEntry(2f, 155f))
        entries.add(BarEntry(3f, 167f))
        entries.add(BarEntry(4f, 168f))
        entries.add(BarEntry(5f, 180f))
        entries.add(BarEntry(6f, 157f))
        entries.add(BarEntry(7f, 167f))
        entries.add(BarEntry(8f, 160f))
        entries.add(BarEntry(9f, 164f))
        entries.add(BarEntry(10f, 178f))
        entries.add(BarEntry(11f, 158f))
        val valueFormatter = ChartValueFormatter("", " h", Constants.MONTHS_LABEL)
        val barData = BarDataSet(entries, null)
        barData.color = resources.getColor(R.color.secondaryColor)
        val data = BarData(barData)
        data.setValueFormatter(valueFormatter)
        data.barWidth = 0.9f
        val chart: HorizontalBarChart =
            ChartUtils.setHorizontalBarChartDefault(findViewById<HorizontalBarChart>(id))
        chart.xAxis.setLabelCount(entries.size, false)
        chart.data = data
        chart.data.setValueTextSize(14f)
        chart.offsetLeftAndRight(20)
        chart.animateXY(1000, 1000)
        chart.setDrawValueAboveBar(false)
        // fixes not showing of values in horizontal chart
        chart.axisLeft.axisMinimum = 0f
        chart.axisLeft.axisMaximum = data.yMax
        chart.setOnChartValueSelectedListener(this)
        chart.xAxis.valueFormatter = valueFormatter
        chart.animate()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.w("values", e.toString())
    }

    override fun onNothingSelected() {
    }
}