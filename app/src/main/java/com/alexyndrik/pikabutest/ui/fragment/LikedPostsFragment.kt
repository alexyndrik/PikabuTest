package com.alexyndrik.pikabutest.ui.fragment

import android.view.View
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.common.LikesProvider
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.view.*


class LikedPostsFragment : BaseFragment() {

    override fun showBaseMessage(view: View) {
        showMessage(view, getString(R.string.no_liked_posts))
    }

    override fun doPostsObserver(view: View, posts: ArrayList<PikabuPost>) {
        if (checkIsEmpty(view)) return

        val likedPosts = ArrayList<PikabuPost>()
        for (post in posts)
            if (LikesProvider.likedPosts.contains(post.id))
                likedPosts.add(post)
        view.feed.adapter = PostAdapter(likedPosts, MainActivity.likedPostLiveData)
        view.feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(view: View, id: Int) {
        if (LikesProvider.likedPosts.contains(id)) {
            for (post in MainActivity.postsLiveData.value?.response!!)
                if (post.id == id) {
                    (view.feed.adapter as PostAdapter).notifyItemInsert(post)
                    break
                }
        } else
            (view.feed.adapter as PostAdapter).notifyItemRemovedById(id)

        checkIsEmpty(view)
    }

    private fun checkIsEmpty(view: View) : Boolean {
        if (LikesProvider.likedPosts.isEmpty()) {
            showMessage(view, getString(R.string.no_liked_posts))
            return true
        }
        return false
    }
}