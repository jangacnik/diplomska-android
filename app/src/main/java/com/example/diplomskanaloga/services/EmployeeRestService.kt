package com.example.diplomskanaloga.services

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.diplomskanaloga.Constants
import com.example.diplomskanaloga.interfaces.VolleyResponse
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

    fun login(context: Context, credentials: LoginCridentials, callback: VolleyResponse) {
        var toast = Toast.makeText(context, "", Toast.LENGTH_SHORT)
        HttpsTrustManager.allowAllSSL()
        val data = JSONObject(gson.toJson(credentials))
        Log.w("data", data.toString())
        val loginUserRequest = JsonObjectRequest(
            Request.Method.POST,
            Constants.baseUrl + "/authenticate",
            data,
            Response.Listener { response ->
                toast.setText("LOGIN SUCCESSFUL")
                toast.show()
                saveToken(
                    response.getString("jwttoken"),
                    response.getString("refreshToken"),
                    context
                )
                callback.onSuccess(response)
            },
            { error ->
                toast.setText("LOGIN UNSUCCESSFUL")
                toast.show()
                callback.onError(error)
            })
        RestQueueService.getInstance(context).addToRequestQueue(loginUserRequest)
    }

    fun saveToken(token: String, refreshToken: String, context: Context) {
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        sharedPrefs.edit().putString("token", token)
            .putString("refresh", refreshToken).apply()
    }

    fun getUserData(context: Context, volleyResponse: VolleyResponse) {
        HttpsTrustManager.allowAllSSL()
        val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
        val jwt = sharedPrefs.getString("token", "")
        val userDataRequest = object : JsonObjectRequest(
            Request.Method.GET,
            Constants.baseUrl + Constants.employeeSufix + "/user",
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

   fun updateUserData(changes: HashMap<String, String>, context: Context, volleyResponse: VolleyResponse) {
       HttpsTrustManager.allowAllSSL()
//       userData.roles = null
       val data = JSONObject(gson.toJson(changes))
       val sharedPrefs = context.getSharedPreferences("jwt", Context.MODE_PRIVATE)
       val jwt = sharedPrefs.getString("token", "")
       val userDataRequest = object : JsonObjectRequest(
           Request.Method.PUT,
           Constants.baseUrl + Constants.employeeSufix + "/update",
           data,
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
