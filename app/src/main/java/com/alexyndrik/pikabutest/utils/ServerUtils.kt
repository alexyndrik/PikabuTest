package com.alexyndrik.pikabutest.utils

import androidx.lifecycle.MutableLiveData
import com.alexyndrik.pikabutest.model.PostModel

object ServerUtils {

    private val posts = ArrayList<PostModel>().apply {
        add(PostModel(1, "Test-1", "test test test test test test test test test test test test test test test"))
        add(
            PostModel(2, "Test-2", "test test test test test test test test test test test test test test test",
            listOf(
                "https:\\/\\/cs4.pikabu.ru\\/post_img\\/2015\\/10\\/21\\/11\\/1445451225_1177082666.jpg",
                "https:\\/\\/cs8.pikabu.ru\\/post_img\\/2017\\/07\\/09\\/11\\/1499623910130079808.jpg",
                "https:\\/\\/cs10.pikabu.ru\\/post_img\\/2019\\/08\\/14\\/5\\/1565762516115177466.jpg"
            )
        )
        )
    }

    fun loadFeed(liveData: MutableLiveData<List<PostModel>>) {
        liveData.value = posts
    }

    fun loadPost(liveData: MutableLiveData<PostModel>, id: Int) {
        for (post in posts)
            if (post.id == id) {
                liveData.value = post
                return
            }
    }

}