package com.example.diplomskanaloga.models

data class MonthlyHours(
    val uuid: String,
    val employeeUuid: String,
    val totalWorkTime: Int,
    val totalSickLeaveTime: Int,
    val totalLeaveTime: Int,
    val totalTime: Int
)
