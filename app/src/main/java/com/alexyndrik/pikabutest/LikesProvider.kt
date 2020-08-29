package com.alexyndrik.pikabutest

import android.app.Activity
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import java.util.*

object LikesProvider {

    var likedPosts = TreeSet<Int>()

    fun saveLikedPosts(activity: Activity) {
        val prefs = activity.getPreferences(AppCompatActivity.MODE_PRIVATE)
        val str = StringBuilder()
        for (i in likedPosts) {
            str.append(i).append(",")
        }
        prefs.edit().putString("liked_posts", str.toString()).apply()
    }

    fun restoreLikedPosts(activity: Activity) {
        val prefs = activity.getPreferences(AppCompatActivity.MODE_PRIVATE)
        val likedPostsString = prefs.getString("liked_posts", "")
        val lp = likedPostsString!!.split(",")
        for (s in lp) {
            if (!TextUtils.isEmpty(s))
                likedPosts.add(Integer.parseInt(s))
        }
    }

}