package com.alexyndrik.pikabutest.service

import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.livedata.AllPostsLiveData
import com.alexyndrik.pikabutest.ui.livedata.PostLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import java.util.*

object PikabuApiClient {

    private const val host = "https://pikabu.ru/page/interview/mobile-app/test-api"

    fun loadAllPosts(queue: RequestQueue) {
        val request = JsonArrayRequest(Request.Method.GET, "$host/feed.php", null, { response ->
            val result = TreeMap<Int, PikabuPost>()
            for (i in 0 until response.length()) {
                val post = Gson().fromJson(response.getJSONObject(i).toString(), PikabuPost::class.java)
                result[post.id] = post
            }
            AllPostsLiveData.value = Response(result)
        }, { error ->
            error.printStackTrace()
            AllPostsLiveData.value = Response(error = Exception(error.localizedMessage))
        })

        queue.add(request)
    }

    fun loadPost(queue: RequestQueue, id: Int) {
        val request = JsonObjectRequest(Request.Method.GET, "$host/story.php?id=$id", null, { response ->
            PostLiveData.value = Response(Gson().fromJson(response.toString(), PikabuPost::class.java))
        }, { error ->
            error.printStackTrace()
            PostLiveData.value = Response(error = Exception(error.localizedMessage))
        })

        queue.add(request)
    }

    data class Response<T>(val data: T? = null, val error: Exception? = null)

}