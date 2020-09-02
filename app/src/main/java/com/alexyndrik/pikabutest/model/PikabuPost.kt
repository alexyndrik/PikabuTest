package com.alexyndrik.pikabutest.model

data class PikabuPost(
    var id: Int = 0,
    var title: String = "",
    var body: String = "",
    var images: ArrayList<String> = ArrayList()
) {

    override fun equals(other: Any?): Boolean {
        if (other is PikabuPost)
            return other.id == id
        return false
    }

    override fun hashCode() = id
}