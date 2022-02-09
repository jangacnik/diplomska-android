package com.example.diplomskanaloga.utils

import com.example.diplomskanaloga.Constants
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class ChartUtils {
    companion object {

        fun setBarChartDefaults(chart: BarChart): BarChart {
            // hide all lines of chart
            chart.axisLeft.setDrawLabels(false)
            chart.axisLeft.setDrawGridLines(false)
            chart.axisLeft.setDrawAxisLine(false)
            chart.axisRight.setDrawLabels(false)
            chart.axisRight.setDrawGridLines(false)
            chart.axisRight.setDrawAxisLine(false)
            chart.xAxis.setDrawAxisLine(false)
            chart.xAxis.setDrawGridLines(false)
            // set labels for bars
            chart.xAxis.granularity = 1f
            chart.xAxis.isGranularityEnabled = true
            chart.xAxis.setCenterAxisLabels(false)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(Constants.WEEKDAY_LABEL_SLO)
            // hide description
            chart.description.text = ""
            // hide legend
            chart.legend.isEnabled = false
            chart.setFitBars(false)
            return chart
        }

        fun setHorizontalBarChartDefault(chart: HorizontalBarChart): HorizontalBarChart {
//            // hide all lines of chart
//            chart.axisLeft.setDrawLabels(false)
//            chart.axisLeft.setDrawGridLines(false)
//            chart.axisLeft.setDrawAxisLine(false)
//            chart.axisRight.setDrawLabels(false)
//            chart.axisRight.setDrawGridLines(false)
//            chart.axisRight.setDrawAxisLine(false)
//            chart.xAxis.setDrawAxisLine(false)
//            chart.xAxis.setDrawGridLines(false)
//            // set labels for bars
//            chart.xAxis.granularity = 1f
//            chart.xAxis.isGranularityEnabled = true
//            chart.xAxis.setCenterAxisLabels(false)
//            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
//            chart.xAxis.valueFormatter = IndexAxisValueFormatter(Constants.MONTHS_LABEL)
//            // hide description
//            chart.description.text = ""
//            // hide legend
//            chart.legend.isEnabled = false
//            chart.setFitBars(true)

            chart.axisLeft.setDrawLabels(false)
            chart.axisLeft.setDrawGridLines(false)
            chart.axisLeft.setDrawAxisLine(false)
            chart.axisRight.setDrawLabels(false)
            chart.axisRight.setDrawGridLines(false)
            chart.axisRight.setDrawAxisLine(false)
            chart.xAxis.setDrawAxisLine(false)
            chart.xAxis.setDrawGridLines(false)
            // set labels for bars
            chart.xAxis.granularity = 1f
            chart.xAxis.isGranularityEnabled = true
            chart.xAxis.setCenterAxisLabels(false)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(Constants.MONTHS_LABEL)
            // hide description
            chart.description.text = ""
            // hide legend
            chart.legend.isEnabled = false
            chart.setFitBars(false)
            return chart
        }
    }
}