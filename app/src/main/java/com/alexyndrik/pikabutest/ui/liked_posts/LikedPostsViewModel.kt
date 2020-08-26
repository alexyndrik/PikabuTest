package com.alexyndrik.pikabutest.ui.liked_posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LikedPostsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is liked posts Fragment"
    }
    val text: LiveData<String> = _text
}