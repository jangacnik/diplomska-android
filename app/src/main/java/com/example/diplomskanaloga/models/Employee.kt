package com.example.diplomskanaloga.models

import com.example.diplomskanaloga.models.enums.Gender
import java.util.*

data class Employee(
    var uuid: String? = null,
    var name: String,
    var surname: String,
    var email: String,
    var phone: String,
    val address: Address,
    var gender: Gender,
    var deviceId: MutableList<Device>? = null,
    var roles: MutableList<Roles>? = null
)
