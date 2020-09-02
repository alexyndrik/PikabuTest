package com.alexyndrik.pikabutest.ui

import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.LikesProvider
import com.alexyndrik.pikabutest.service.PikabuApiClient.Response
import com.alexyndrik.pikabutest.ui.livedata.LikedPostIdLiveData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_post.view.*

object PostPresenter {

    fun fillPostInfo(itemView: View, post: PikabuPost, isFeed: Boolean) {
        itemView.post_title.text = post.title

        showLike(itemView.is_liked, post.id)
        itemView.is_liked.setOnClickListener {
            if (LikesProvider.isLiked(post.id))
                LikesProvider.unlike(post.id)
            else
                LikesProvider.like(post.id)
            showLike(itemView.is_liked, post.id)

            LikedPostIdLiveData.value = Response(post.id)
        }

        itemView.post_body.text = post.body
        if (isFeed) {
            itemView.post_body.maxLines = 1
            itemView.post_body.ellipsize = TextUtils.TruncateAt.END
        }

        itemView.post_images.removeAllViews()
        if (isFeed && post.images.isNotEmpty()) {
            addImage(itemView.post_images, post.images[0])
        } else
            for (imgUrl in post.images)
                addImage(itemView.post_images, imgUrl)
    }

    private fun showLike(view: ImageButton, id: Int) {
        if (LikesProvider.isLiked(id))
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_liked_24))
        else
            view.setImageDrawable(ContextCompat.getDrawable(view.context,
                R.drawable.ic_not_liked_24
            ))
    }

    private fun addImage(layout: LinearLayout, imgUrl: String) {
        val imageView = ImageView(layout.context)
        imageView.scaleType = ImageView.ScaleType.FIT_CENTER
        imageView.adjustViewBounds = true
        imageView.setPadding(0, 8, 0, 0)

        Picasso.get()
            .load(imgUrl)
            .error(R.drawable.image_error)
            .into(imageView)

        layout.addView(imageView)
    }

}