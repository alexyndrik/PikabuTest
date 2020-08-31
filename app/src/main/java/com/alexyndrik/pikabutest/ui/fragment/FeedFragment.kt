package com.alexyndrik.pikabutest.ui.fragment

import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.*


class FeedFragment : BaseFragment() {

    override fun doPostsObserver(posts: ArrayList<PikabuPost>) {
        feed.adapter = PostAdapter(posts, MainActivity.likedPostLiveData)
        feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(id: Int) {
        (feed.adapter as PostAdapter).notifyItemChangedById(id)
    }
}