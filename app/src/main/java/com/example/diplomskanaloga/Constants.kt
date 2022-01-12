package com.example.diplomskanaloga

import java.util.*

object Constants {
    const val baseUrl = "https://192.168.0.172:8082";
    const val employeeSufix = "/api/v1/employees";
    const val workHourSufix = "/api/v1/hours";
    const val monthlyHoursSufix = "/api/v1/monthly";

    // Labels for charts
    val WEEKDAY_LABEL: List<String> = Collections.unmodifiableList(listOf("PON", "TOR", "SRE", "CET", "PET", "SOB", "NED"))
    val MONTHS_LABEL: List<String> = Collections.unmodifiableList(listOf("JAN", "FEB", "MAR", "APR", "MAJ", "JUN", "JUL", "AUG", "SEP", "OKT", "NOV", "DEC"))
}