package com.alexyndrik.pikabutest.utils

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.model.PostModel

object PikabuServerUtils {

    private const val host = "https://pikabu.ru/page/interview/mobile-app/test-api"
    private const val feed = "/feed.php"
    private const val story = "/story.php?id=%d"

    fun loadFeed(liveData: MutableLiveData<ArrayList<PostModel>>) {
        VolleyUtils.getJsonArray(host + feed, {
            response ->
                val result = ArrayList<PostModel>()
                for (i in 0 until response.length())
                    result.add(PostModel(response.getJSONObject(i)))
                liveData.value = result
        }, {
                error -> error.printStackTrace()
        })
    }

    fun loadPost(liveData: MutableLiveData<PostModel>, id: Int) {
        VolleyUtils.getJsonObject(host + String.format(story, id), {
                response -> liveData.value = PostModel(response)
        }, {
                error -> error.printStackTrace()
        })
    }

}