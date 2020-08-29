package com.alexyndrik.pikabutest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.ExtraName
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.activity.PostActivity
import com.alexyndrik.pikabutest.utils.PostUtils


class PostAdapter(
    private val fragment: Fragment,
    private val requestCode: Int,
    private val posts: List<PostModel>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(fragment, requestCode, posts[position])
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_post, parent, false)) {

        fun bind(fragment: Fragment, requestCode: Int, post: PostModel) {
            itemView.setOnClickListener {
                val intent = Intent(fragment.context, PostActivity::class.java)
                intent.putExtra(ExtraName.position, adapterPosition)
                intent.putExtra(ExtraName.post_id, post.id)
                fragment.startActivityForResult(intent, requestCode)
            }

            PostUtils.fillPostInfo(itemView, post, false)
        }
    }

}