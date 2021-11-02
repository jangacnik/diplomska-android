package com.example.diplomskanaloga.models

import java.time.LocalDateTime

data class WorkhourLog(
    val uuid: String,
    val employeeUuid: String,
    val startTime: LocalDateTime,
    val LastPing: LocalDateTime
)
