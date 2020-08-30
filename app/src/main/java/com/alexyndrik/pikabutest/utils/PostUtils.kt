package com.alexyndrik.pikabutest.utils

import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.ImageGalleryAdapter
import com.alexyndrik.pikabutest.model.PostModel
import kotlinx.android.synthetic.main.view_post.view.*

object PostUtils {

    fun fillPostInfo(itemView: View, post: PostModel, isPost: Boolean, liveData: MutableLiveData<Int>) {
        itemView.post_title.text = post.title

        showLike(itemView.is_liked, post.id)
        itemView.is_liked.setOnClickListener {
            if (LikesProvider.likedPosts.contains(post.id))
                LikesProvider.likedPosts.remove(post.id)
            else
                LikesProvider.likedPosts.add(post.id)
            showLike(itemView.is_liked, post.id)

            liveData.value = post.id
        }

        if (TextUtils.isEmpty(post.body))
            itemView.post_body.visibility = View.GONE
        else
            itemView.post_body.text = post.body
        if (!isPost) {
            itemView.post_body.maxLines = 1
            itemView.post_body.ellipsize = TextUtils.TruncateAt.END
        }

        if (post.images.isEmpty())
            itemView.post_images.visibility = View.GONE
        else
            itemView.post_images.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(context, if (isPost) 2 else post.images.size)
                adapter = ImageGalleryAdapter(context, post.images, isPost)
            }
    }

    private fun showLike(view: ImageButton, id: Int) {
        if (LikesProvider.likedPosts.contains(id))
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_liked_24))
        else
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_not_liked_24))
    }

}