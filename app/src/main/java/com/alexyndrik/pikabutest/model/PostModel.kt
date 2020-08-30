package com.alexyndrik.pikabutest.model

data class PostModel(
    val id: Int,
    val title: String,
    val body: String = "",
    val images: List<String> = ArrayList()
) {
    override fun equals(other: Any?): Boolean {
        if (other is PostModel)
            return other.id == id
        return false
    }

    override fun hashCode() = id
}