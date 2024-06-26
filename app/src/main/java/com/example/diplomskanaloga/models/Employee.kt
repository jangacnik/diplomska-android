package com.example.diplomskanaloga.models

import com.example.diplomskanaloga.models.enums.Gender
import java.util.*
import kotlin.collections.ArrayList

data class Employee(
    var uuid: String? = null,
    var name: String,
    var surname: String,
    var email: String,
    var phone: String,
    var password: String,
    val address: Address,
    var gender: Gender,
    var deviceId: MutableList<Device>? = null,
    var roles: ArrayList<Roles>? = null
)
