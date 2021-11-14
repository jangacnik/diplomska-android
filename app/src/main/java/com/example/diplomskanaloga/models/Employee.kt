package com.example.diplomskanaloga.models

import com.example.diplomskanaloga.models.enums.Gender

data class Employee(
    var name: String,
    var surname: String,
    var email: String,
    var phone: String,
    val address: Address,
    var gender: Gender,
    var deviceId: MutableList<String>? = null,
    var uuid: String? = null,
)
