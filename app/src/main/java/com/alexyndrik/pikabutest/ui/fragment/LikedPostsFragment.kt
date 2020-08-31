package com.alexyndrik.pikabutest.ui.fragment

import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.common.LikesProvider
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.*


class LikedPostsFragment : BaseFragment() {

    override fun doPostsObserver(posts: ArrayList<PikabuPost>) {
        if (checkIsEmpty()) return

        val likedPosts = ArrayList<PikabuPost>()
        for (post in posts)
            if (LikesProvider.likedPosts.contains(post.id))
                likedPosts.add(post)
        feed.adapter = PostAdapter(likedPosts, MainActivity.likedPostLiveData)
        feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(id: Int) {
        if (LikesProvider.likedPosts.contains(id)) {
            for (post in MainActivity.postsLiveData.value?.response!!)
                if (post.id == id) {
                    (feed.adapter as PostAdapter).notifyItemInsert(post)
                    break
                }
        } else
            (feed.adapter as PostAdapter).notifyItemRemovedById(id)

        checkIsEmpty()
    }

    private fun checkIsEmpty() : Boolean {
        if (LikesProvider.likedPosts.isEmpty()) {
            view?.let { showMessage(it, getString(R.string.no_liked_posts)) }
            return true
        }
        return false
    }
}