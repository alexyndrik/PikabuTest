package com.alexyndrik.pikabutest.utils

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.alexyndrik.pikabutest.ExtraName
import java.util.*

object LikesProvider {

    var likedPosts = TreeSet<Int>()

    private const val name = "pikabutest"
    private const val delimiter = ","

    fun saveLikedPosts(activity: Activity) {
        val prefs = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
        val str = StringBuilder()
        for (i in likedPosts) {
            str.append(i).append(delimiter)
        }
        prefs.edit().putString(ExtraName.liked_posts, str.toString()).apply()
    }

    fun restoreLikedPosts(activity: Activity) {
        val prefs = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
        val likedPostsString = prefs.getString(ExtraName.liked_posts, "")
        val lp = likedPostsString!!.split(delimiter)
        for (s in lp) {
            if (!TextUtils.isEmpty(s))
                likedPosts.add(Integer.parseInt(s))
        }
    }

}