package com.alexyndrik.pikabutest.model

import org.json.JSONObject

data class PostModel(
    var id: Int = 0,
    var title: String = "",
    var body: String = "",
    var images: ArrayList<String> = ArrayList()
) {

    constructor(jsonObject: JSONObject) : this() {
        id = jsonObject.getInt("id")
        title = if (jsonObject.has("title")) jsonObject.getString("title") else ""
        body = if (jsonObject.has("body")) jsonObject.getString("body") else ""
        images = ArrayList()
        if (jsonObject.has("images")) {
            val jsonImages = jsonObject.getJSONArray("images")
            for (j in 0 until jsonImages.length())
                images.add(jsonImages.getString(j))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other is PostModel)
            return other.id == id
        return false
    }

    override fun hashCode() = id
}