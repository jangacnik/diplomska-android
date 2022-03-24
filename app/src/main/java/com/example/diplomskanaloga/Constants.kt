package com.example.diplomskanaloga

import java.util.*

object Constants {
    const val baseUrl = "https://192.168.0.172:8080";
    const val employeeSufix = "/api/v1/employees";
    const val workHourSufix = "/api/v1/hours";
    const val monthlyHoursSufix = "/api/v1/monthly";

    // Labels for charts
    val WEEKDAY_LABEL: List<String> = Collections.unmodifiableList(listOf("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"))
    val WEEKDAY_LABEL_SLO: List<String> = Collections.unmodifiableList(listOf("PON", "TOR", "SRE", "CET", "PET", "SOB", "NED"))
    val MONTHS_LABEL: List<String> = Collections.unmodifiableList(listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
}