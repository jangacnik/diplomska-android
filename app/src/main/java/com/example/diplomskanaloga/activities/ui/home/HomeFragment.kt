package com.example.diplomskanaloga.activities.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.VolleyError
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.R
import com.example.diplomskanaloga.databinding.FragmentHomeBinding
import com.example.diplomskanaloga.interfaces.VolleyResponse
import com.example.diplomskanaloga.services.EmployeeRestService
import com.example.diplomskanaloga.services.WorkHoursRestService
import com.example.diplomskanaloga.utils.ChartUtils
import com.example.diplomskanaloga.utils.ChartValueFormatter
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment(), OnChartValueSelectedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var employeeRestService: EmployeeRestService
    lateinit var workHoursRestService: WorkHoursRestService
    lateinit var weeklyReport: Map<String, Long>
    lateinit var monthlyReport: Map<String, Long>

    // elements
    lateinit var hoursThisWeekTextview: TextView

    lateinit var barChart: BarChart
    lateinit var horizontalBarChart: HorizontalBarChart

    // util
    val gson = Gson()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        employeeRestService = EmployeeRestService()
        workHoursRestService = WorkHoursRestService()
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        barChart = binding.weeklyBarChart
        horizontalBarChart = binding.yearlyOverviewChart
        hoursThisWeekTextview = root.findViewById(R.id.textView_week_total)
        getWeeklyReport(root)
        getMonthlyReport(root)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getWeeklyReport(root: View) {
        workHoursRestService.getWeeklyReport(this.requireContext(), object : VolleyResponse {
            override fun onSuccess(response: Any?) {
                val type = object : TypeToken<Map<String, Long>>() {}.type
                weeklyReport = gson.fromJson(response.toString(), type)
//                Log.w("Response", weeklyReport)
                initWeeklyBarChart(weeklyReport)
            }
            override fun onError(error: VolleyError?) {
                if (error != null) {
                    Log.e("Response Error", error.networkResponse.toString())
                }
            }
        })
    }

    fun getMonthlyReport(root: View) {
        workHoursRestService.getMonthlyReport(this.requireContext(), object : VolleyResponse {
            override fun onSuccess(response: Any?) {
                val type = object : TypeToken<Map<String, Long>>() {}.type
                monthlyReport = gson.fromJson(response.toString(), type)
//                Log.w("Response", weeklyReport)
                initMonthlyChart(monthlyReport)
            }

            override fun onError(error: VolleyError?) {
                if (error != null) {
                    Log.e("Response Error", error.networkResponse.toString())
                }
            }
        })
    }

    fun initWeeklyBarChart(values: Map<String, Long>) {
        val entries: MutableList<BarEntry> = ArrayList()
        Constants.WEEKDAY_LABEL.forEach { v ->
            if (values[v] != null) {
                val fVal = values.get(v)!!.toFloat()
                var time: Float = fVal.div(60f)
                entries.add(BarEntry(Constants.WEEKDAY_LABEL.indexOf(v).toFloat(), time))
            } else {
                entries.add(BarEntry(Constants.WEEKDAY_LABEL.indexOf(v).toFloat(), 0f))
            }
        }
        val barData = BarDataSet(entries, null)
        barData.color = resources.getColor(R.color.secondaryColor)
        val data = BarData(barData)
        data.barWidth = 0.9f
        val chart: BarChart = ChartUtils.setBarChartDefaults(barChart)
        chart.data = data
        chart.animateXY(1000, 1000)
        // set text size of bar values
        chart.data.setValueTextSize(14f)

        chart.animate()
        var totalMin: Long = 0
        var totalHour: Float = 0f
        for ((key, value) in values) {
            totalMin += value
        }
        totalHour = totalMin.toFloat().div(60).toFloat()
        hoursThisWeekTextview.setText("$totalHour hours")
    }

    fun initMonthlyChart(values: Map<String, Long>) {
        val entries: MutableList<BarEntry> = ArrayList()

        Constants.MONTHS_LABEL.forEach { v ->
            if (values[v] != null) {
                val fVal = values.get(v)!!.toFloat()
                var time: Float = fVal.div(60f)
                entries.add(
                    BarEntry(
                        Constants.MONTHS_LABEL.indexOf(v).toFloat()-1 ,
                        time
                    )
                ) // -1 because january is 1 not 0 in backend
            } else {
                entries.add(BarEntry(Constants.MONTHS_LABEL.indexOf(v).toFloat(), 0f))
            }
        }
        val valueFormatter = ChartValueFormatter(
            "",
            " h",
            Constants.MONTHS_LABEL)
        val barData = BarDataSet(entries, null)
        barData.color = resources.getColor(R.color.secondaryColor)
        val data = BarData(barData)
        data.setValueFormatter(valueFormatter)
        data.barWidth = 0.9f
        val chart: HorizontalBarChart =
            ChartUtils.setHorizontalBarChartDefault(horizontalBarChart)
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