package com.alexyndrik.pikabutest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.view.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_base, container, false)
        retainInstance = true

        initView(root)
        initObservers(root)

        return root
    }

    private fun initView(view: View) {
        view.feed.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = parentFragment?.let { PostAdapter(ArrayList(), MainActivity.likedPost) }
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initObservers(view: View) {
        val postsObserver = Observer<ArrayList<PostModel>> {
            doPostsObserver(view, it)
        }

        MainActivity.posts.observe(viewLifecycleOwner, postsObserver)

        val likedPostObserver = Observer<Int> {
            doLikedPostObserver(view, it)
        }

        MainActivity.likedPost.observe(viewLifecycleOwner, likedPostObserver)
    }

    abstract fun doPostsObserver(view: View, posts: ArrayList<PostModel>)
    abstract fun doLikedPostObserver(view: View, id: Int)

}