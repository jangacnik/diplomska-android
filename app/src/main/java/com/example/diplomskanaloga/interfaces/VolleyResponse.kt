package com.example.diplomskanaloga.interfaces

import com.android.volley.VolleyError

interface VolleyResponse {
    fun onSuccess(response: Any?)
    fun onError(error: VolleyError?)
}
