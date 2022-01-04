package com.example.diplomskanaloga.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.models.Address
import com.example.diplomskanaloga.models.Employee
import com.example.diplomskanaloga.models.JWT
import com.example.diplomskanaloga.models.LoginCridentials
import com.example.diplomskanaloga.models.enums.Gender
import com.google.gson.Gson
import org.json.JSONObject
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession
import com.example.diplomskanaloga.services.HttpsTrustManager

class EmployeeRestService constructor() {

    val gson = Gson()

    fun login(context: Context, credentials: LoginCridentials) {
        var toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        HttpsTrustManager.allowAllSSL()
        val data = JSONObject(gson.toJson(credentials))
        val loginUserRequest = JsonObjectRequest(
            Request.Method.POST,
            Constants.baseUrl + "/authenticate",
            data,
            Response.Listener { response ->
                Log.d("RESPONSE", response.toString())
                toast.setText("LOGIN SUCCESSFUL")
                toast.show()
                saveToken(response.getString("jwttoken"), response.getString("refreshToken"), context)
            },
            { error ->
                Log.e("ERROR", String(error.networkResponse.data))
                toast.setText("LOGIN UNSUCCESSFUL")
                toast.show()

            })
        RestQueueService.getInstance(context).addToRequestQueue(loginUserRequest)
    }

    fun saveToken(token: String, refreshToken: String, context: Context) {
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("token", token)
            .putString("refresh", refreshToken).apply()
    }

    fun getUserData(context: Context) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : JsonObjectRequest(
            Request.Method.GET,
            Constants.baseUrl + Constants.employeeSufix + "/user",
            null,
            Response.Listener {
                response -> Log.d("test", response.toString())
            }, {
                error ->Log.e("test", String(error.networkResponse.data))
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
