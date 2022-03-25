package com.example.diplomskanaloga.utils

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat


class ChartValueFormatter(val valuePrefix: String, val valueSuffix: String,val labelValues: List<String>) : ValueFormatter() {
    private var mFormat: DecimalFormat? = null

    init {
        mFormat = DecimalFormat("###,###,##0.00")
    }

    override fun getFormattedValue(value: Float): String {
        if(value <= 0f) {
            return ""
        }
        return this.valuePrefix + mFormat!!.format(value)  + this.valueSuffix
    }

    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return this.labelValues[value.toInt()]
    }
}