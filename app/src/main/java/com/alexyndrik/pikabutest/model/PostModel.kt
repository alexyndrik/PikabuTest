package com.alexyndrik.pikabutest.model

data class PostModel(
    val id: Int,
    val title: String,
    val body: String = "",
    val images: List<String> = ArrayList()
)