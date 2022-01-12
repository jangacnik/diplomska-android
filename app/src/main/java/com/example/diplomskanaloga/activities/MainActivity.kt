package com.example.diplomskanaloga.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.utils.ChartUtils
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        barTest()
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