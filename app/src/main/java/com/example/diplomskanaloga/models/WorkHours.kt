package com.example.diplomskanaloga.models

import com.example.diplomskanaloga.models.enums.WorkHourType
import java.time.LocalDateTime

data class WorkHours(
    val uuid: String,
    val employeeUuid: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val workHourType: WorkHourType,
    val totalTime: Long
)
