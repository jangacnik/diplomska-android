package com.example.diplomskanaloga.services

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.models.Address
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.models.enums.Gender
import com.google.gson.Gson
import org.json.JSONObject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession
import com.example.diplomskanaloga.services.HttpsTrustManager

class EmployeeRestService constructor() {

    val gson = Gson()

    fun registerNewUser(context: Context) {
        HttpsTrustManager.allowAllSSL()
        val model = Employee(
            "Jan Alojz",
            "Gacnik",
            "gacnik221200@gmail.com",
            "0871311222020008",
            Address("Drav po 18", "2135", "Maribor", "Slovenia"),
            Gender.MALE
        )
        val jsonObject = JSONObject(gson.toJson(model))
        var reisterUserRequest = JsonObjectRequest(Request.Method.POST, Constants.baseUrl+Constants.employeeSufix+"/new",jsonObject, Response.Listener<JSONObject> {
            response ->  Log.d("Response", "success")
        }, {
            error -> Log.e("Error", String(error.networkResponse.data))
        })
        RestQueueService.getInstance(context).addToRequestQueue(reisterUserRequest)

    }


}
