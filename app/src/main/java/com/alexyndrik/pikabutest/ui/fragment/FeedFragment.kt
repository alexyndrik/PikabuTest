package com.alexyndrik.pikabutest.ui.fragment

import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.PostAdapter
import kotlinx.android.synthetic.main.fragment_base.*


class FeedFragment : BaseFragment() {

    override fun doPostsObserver(posts: Map<Int, PikabuPost>) {
        feed.adapter = PostAdapter(posts.toList().map { it.second } as ArrayList<PikabuPost>)
        feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(id: Int) {
        (feed.adapter as PostAdapter).notifyItemChangedById(id)
    }
}