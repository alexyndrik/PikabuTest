package com.alexyndrik.pikabutest.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.LikesProvider
import com.alexyndrik.pikabutest.PostModel
import com.alexyndrik.pikabutest.R
import kotlinx.android.synthetic.main.view_post.view.*


class PostAdapter(
    private val context: Context,
    private val posts: List<PostModel>,
    private val isPost: Boolean
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class PostViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_post, parent, false)) {

        fun bind(post: PostModel) {
            itemView.title.text = post.title

            liked(itemView.liked, post.id)
            itemView.liked.setOnClickListener {
                if (LikesProvider.likedPosts.contains(post.id))
                    LikesProvider.likedPosts.remove(post.id)
                else
                    LikesProvider.likedPosts.add(post.id)
                liked(itemView.liked, post.id)
            }

            if (TextUtils.isEmpty(post.body))
                itemView.body.visibility = View.GONE
            else
                itemView.body.text = post.body
            if (!isPost) {
                itemView.body.maxLines = 1
                itemView.body.ellipsize = TextUtils.TruncateAt.END
            }

            if (post.images.isEmpty())
                itemView.images.visibility = View.GONE
            else
                itemView.images.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(context, if (isPost) 2 else post.images.size)
                    adapter = ImageGalleryAdapter(context, post.images, isPost)
                }
        }
    }

    fun liked(view: ImageButton, id: Int) {
        if (LikesProvider.likedPosts.contains(id))
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_liked_24))
        else
            view.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_not_liked_24))
    }

}