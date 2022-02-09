package com.example.diplomskanaloga.services

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.interfaces.VolleyResponse

class WorkHoursRestService constructor() {

    fun getWeeklyReport(context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : JsonObjectRequest(
            Request.Method.GET,
            Constants.baseUrl + Constants.workHourSufix + "/week/current",
            null,
            Response.Listener { response ->
                volleyResponse.onSuccess(response)
            }, { error ->
                volleyResponse.onError(error)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $jwt"
                return headers
            }
        }
        RestQueueService.getInstance(context).addToRequestQueue(userDataRequest)
    }

    fun getMonthlyReport(context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : JsonObjectRequest(
            Request.Method.GET,
            Constants.baseUrl +  "/api/v1/monthly/",
            null,
            Response.Listener { response ->
                volleyResponse.onSuccess(response)
            }, { error ->
                volleyResponse.onError(error)
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = "Bearer $jwt"
                return headers
            }
        }
        RestQueueService.getInstance(context).addToRequestQueue(userDataRequest)
    }
}