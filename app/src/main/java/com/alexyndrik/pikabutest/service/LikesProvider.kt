package com.alexyndrik.pikabutest.service

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.alexyndrik.pikabutest.Const
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
        prefs.edit().putString(Const.PrefName.LIKED_POSTS, str.toString()).apply()
    }

    fun restoreLikedPosts(activity: Activity) {
        val prefs = activity.getSharedPreferences(name, Context.MODE_PRIVATE)
        val likedPostsString = prefs.getString(Const.PrefName.LIKED_POSTS, "")
        val lp = likedPostsString!!.split(delimiter)
        for (s in lp) {
            if (!TextUtils.isEmpty(s))
                likedPosts.add(Integer.parseInt(s))
        }
    }

}