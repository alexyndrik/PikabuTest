package com.alexyndrik.pikabutest.ui.fragment

import android.view.View
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.view.*


class FeedFragment : BaseFragment() {

    override fun doPostsObserver(view: View, posts: ArrayList<PostModel>) {
        view.feed.adapter = PostAdapter(posts, MainActivity.likedPost)
        view.feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(view: View, id: Int) {
        (view.feed.adapter as PostAdapter).notifyItemChangedById(id)
    }
}