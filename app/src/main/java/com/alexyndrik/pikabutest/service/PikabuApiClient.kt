package com.alexyndrik.pikabutest.service

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.model.PikabuPost
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import java.util.*

object PikabuApiClient {

    private const val host = "https://pikabu.ru/page/interview/mobile-app/test-api"

    fun loadAllPosts(queue: RequestQueue, liveData: MutableLiveData<Response<Map<Int, PikabuPost>>>) {
        val request = JsonArrayRequest(Request.Method.GET, "$host/feed.php", null, { response ->
            val result = TreeMap<Int, PikabuPost>()
            for (i in 0 until response.length()) {
                val post = PikabuPost(response.getJSONObject(i))
                result[post.id] = post
            }
            liveData.value = Response(result)
        }, { error ->
            error.printStackTrace()
            liveData.value = Response(error = Exception(error.localizedMessage))
        })

        queue.add(request)
    }

    fun loadPost(queue: RequestQueue, liveData: MutableLiveData<Response<PikabuPost>>, id: Int) {
        val request = JsonObjectRequest(Request.Method.GET, "$host/story.php?id=$id", null, { response ->
            liveData.value = Response(PikabuPost(response))
        }, { error ->
            error.printStackTrace()
            liveData.value = Response(error = Exception(error.localizedMessage))
        })

        queue.add(request)
    }

    data class Response<T>(val data: T? = null, val error: Exception? = null)

}