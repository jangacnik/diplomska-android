package com.example.diplomskanaloga.services

import android.content.Context
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
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

    fun getCurrentWorkTime(context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : com.android.volley.toolbox.StringRequest(
            Request.Method.GET,
            Constants.baseUrl +  "/api/v1/hours/current",
            Response.Listener { response: String ->
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

    fun getWorkStatus(context: Context, volleyResponse: VolleyResponse) {

        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : com.android.volley.toolbox.StringRequest(
            Request.Method.GET,
            Constants.baseUrl +  "/api/v1/hours/status",
            Response.Listener { response: String ->
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

    fun startWorkday(uuid: String,context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : com.android.volley.toolbox.StringRequest(
            Request.Method.POST,
            Constants.baseUrl +  "/api/v1/hours/new/$uuid/WORK",
            Response.Listener { response: String ->
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
    fun stopWorkday(uuid: String,context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : JsonObjectRequest(
            Request.Method.PUT,
            Constants.baseUrl +  "/api/v1/hours/end/$uuid",
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
    fun startBreak(uuid: String,context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : com.android.volley.toolbox.StringRequest(
            Request.Method.POST,
            Constants.baseUrl +  "/api/v1/hours/break/start/$uuid",
            Response.Listener { response: String ->
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
    fun stopBreak(uuid: String,context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : com.android.volley.toolbox.StringRequest(
            Request.Method.PUT,
            Constants.baseUrl +  "/api/v1/hours/break/end/$uuid",
            Response.Listener { response: String ->
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