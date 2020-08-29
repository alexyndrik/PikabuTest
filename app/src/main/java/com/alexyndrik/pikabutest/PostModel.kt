package com.alexyndrik.pikabutest

data class PostModel(
    val id: Int,
    val title: String,
    val body: String = "",
    val images: List<String> = ArrayList(),
)