package com.alexyndrik.pikabutest.ui.fragment

import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.LikesProvider
import com.alexyndrik.pikabutest.ui.PostAdapter
import com.alexyndrik.pikabutest.ui.livedata.AllPostsLiveData
import kotlinx.android.synthetic.main.fragment_base.*


class LikedPostsFragment : BaseFragment() {

    override fun doPostsObserver(posts: Map<Int, PikabuPost>) {
        if (checkIsEmpty()) return

        val likedPosts = ArrayList<PikabuPost>()
        val likedPostsIterator = LikesProvider.iterator()
        while (likedPostsIterator.hasNext()) {
            posts[likedPostsIterator.next()]?.let { likedPosts.add(it) }
        }

        feed.adapter = PostAdapter(likedPosts)
        feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(id: Int) {
        if (LikesProvider.isLiked(id)) {
            AllPostsLiveData.value?.data?.get(id)?.let { (feed.adapter as PostAdapter).insertItem(it) }
        } else
            (feed.adapter as PostAdapter).removeItemById(id)

        checkIsEmpty()
    }

    private fun checkIsEmpty() : Boolean {
        if (LikesProvider.isEmpty()) {
            view?.let { showMessage(it, getString(R.string.no_liked_posts)) }
            return true
        }
        return false
    }
}