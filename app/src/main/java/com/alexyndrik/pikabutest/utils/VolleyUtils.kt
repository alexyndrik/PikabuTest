package com.alexyndrik.pikabutest.utils

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

object VolleyUtils {

    lateinit var queue: RequestQueue

    fun init(context: Context) {
        queue = Volley.newRequestQueue(context)
    }

    fun getJsonArray(url: String, listener: Response.Listener<JSONArray>, errorListener: Response.ErrorListener) {
        val request = JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener)
        queue.add(request)
    }

    fun getJsonObject(url: String, listener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener) {
        val request = JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener)
        queue.add(request)
    }

}