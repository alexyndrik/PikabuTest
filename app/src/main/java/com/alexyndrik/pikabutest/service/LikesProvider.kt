package com.alexyndrik.pikabutest.service

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import com.alexyndrik.pikabutest.Const

object LikesProvider {

    private val likedPosts = ArrayList<Int>()

    private const val name = "pikabutest"
    private const val delimiter = ","

    fun likesCount() = likedPosts.size

    fun iterator() = likedPosts.iterator()

    fun isLiked(id: Int) = likedPosts.contains(id)

    fun like(id: Int) = if (!isLiked(id)) likedPosts.add(id) else false

    fun unlike(id: Int) = likedPosts.remove(id)

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