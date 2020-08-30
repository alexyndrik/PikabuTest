package com.alexyndrik.pikabutest.ui.fragment

import android.view.View
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import com.alexyndrik.pikabutest.utils.LikesProvider
import kotlinx.android.synthetic.main.fragment_base.view.*


class LikedPostsFragment : BaseFragment() {

    override fun doPostsObserver(view: View, posts: ArrayList<PostModel>) {
        val likedPosts = ArrayList<PostModel>()
        for (post in posts)
            if (LikesProvider.likedPosts.contains(post.id))
                likedPosts.add(post)
        view.feed.adapter = PostAdapter(likedPosts, MainActivity.likedPost)
        view.feed.adapter?.notifyDataSetChanged()
    }

    override fun doLikedPostObserver(view: View, id: Int) {
        if (LikesProvider.likedPosts.contains(id)) {
            for (post in MainActivity.posts.value!!)
                if (post.id == id) {
                    (view.feed.adapter as PostAdapter).notifyItemInsert(post)
                    break
                }
        } else
            (view.feed.adapter as PostAdapter).notifyItemRemovedById(id)
    }
}