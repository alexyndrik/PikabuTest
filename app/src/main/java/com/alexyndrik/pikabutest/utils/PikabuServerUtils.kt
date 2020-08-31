package com.alexyndrik.pikabutest.utils

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.model.PikabuResponse

object PikabuServerUtils {

    private const val host = "https://pikabu.ru/page/interview/mobile-app/test-api"
    private const val feed = "/feed.php"
    private const val story = "/story.php?id=%d"

    fun loadFeed(liveData: MutableLiveData<PikabuResponse<ArrayList<PikabuPost>>>) {
        VolleyUtils.getJsonArray(host + feed, {
            response ->
                val result = ArrayList<PikabuPost>()
                for (i in 0 until response.length())
                    result.add(PikabuPost(response.getJSONObject(i)))
                liveData.value = PikabuResponse(result)
        }, {
                error -> error.printStackTrace()
            liveData.value = PikabuResponse(error = Exception(error.localizedMessage))
        })
    }

    fun loadPost(liveData: MutableLiveData<PikabuResponse<PikabuPost>>, id: Int) {
        VolleyUtils.getJsonObject(host + String.format(story, id), {
                response -> liveData.value = PikabuResponse(PikabuPost(response))
        }, {
                error -> error.printStackTrace()
            liveData.value = PikabuResponse(error = Exception(error.localizedMessage))
        })
    }

}