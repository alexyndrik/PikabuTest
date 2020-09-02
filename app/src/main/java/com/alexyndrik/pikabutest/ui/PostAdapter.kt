package com.alexyndrik.pikabutest.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.ui.activity.PostActivity


class PostAdapter(
    private val posts: ArrayList<PikabuPost>,
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    fun notifyItemChangedById(id: Int) {
        for (position in posts.indices)
            if (posts[position].id == id) {
                notifyItemChanged(position)
                return
            }
    }

    fun insertItem(post: PikabuPost) {
        if (posts.contains(post))
            removeItemById(post.id)
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }

    fun removeItemById(id: Int) {
        for (position in posts.indices)
            if (posts[position].id == id) {
                posts.removeAt(position)
                notifyItemRemoved(position)
                return
            }
    }

    class PostViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_post, parent, false)) {

        fun bind(post: PikabuPost) {
            itemView.setOnClickListener {
                val intent = Intent(it.context, PostActivity::class.java)
                intent.putExtra(Const.ExtraName.POST_ID, post.id)
                it.context.startActivity(intent)
            }

            PostPresenter.fillPostInfo(itemView, post, true)
        }
    }

}