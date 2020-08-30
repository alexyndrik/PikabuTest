package com.alexyndrik.pikabutest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.activity.PostActivity
import com.alexyndrik.pikabutest.utils.PostUtils


class PostAdapter(
    private val posts: ArrayList<PostModel>,
    private var liveData: MutableLiveData<Int>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position], liveData)
    }

    override fun getItemCount() = posts.size

    fun notifyItemChangedById(id: Int) {
        for (position in posts.indices)
            if (posts[position].id == id) {
                notifyItemChanged(position)
                return
            }
    }

    fun notifyItemInsert(post: PostModel) {
        if (posts.contains(post))
            return
        posts.add(post)
        notifyItemInserted(posts.size - 1)
    }

    fun notifyItemRemovedById(id: Int) {
        for (position in posts.indices)
            if (posts[position].id == id) {
                posts.removeAt(position)
                notifyItemRemoved(position)
                return
            }
    }

    class PostViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.view_post, parent, false)) {

        fun bind(post: PostModel, liveData: MutableLiveData<Int>) {
            itemView.setOnClickListener {
                println("click ${post.title} -  ${post.id}")
                val intent = Intent(it.context, PostActivity::class.java)
                intent.putExtra(Const.POST_ID, post.id)
                it.context.startActivity(intent)
            }

            PostUtils.fillPostInfo(itemView, post, false, liveData)
        }
    }

}