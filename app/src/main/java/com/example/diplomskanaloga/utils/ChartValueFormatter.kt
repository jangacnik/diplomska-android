package com.example.diplomskanaloga.utils

import com.example.diplomskanaloga.Constants
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class ChartValueFormatter(val valuePrefix: String, val valueSuffix: String,val labelValues: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return this.valuePrefix + value.toString() + this.valueSuffix
    }

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return this.labelValues[value.toInt()]
    }
}