package com.alexyndrik.pikabutest.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexyndrik.pikabutest.ExtraName
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.utils.ServerUtils
import kotlinx.android.synthetic.main.fragment_feed.view.*


class FeedFragment : Fragment() {

    private val REQUEST_CODE = 123

    private lateinit var feedViewModel: FeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feed, container, false)

        feedViewModel = FeedViewModel()

        init(root)
        loadFeed(root)

        return root
    }

    private fun init(view: View) {
        view.feed.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = parentFragment?.let { PostAdapter(it, REQUEST_CODE, ArrayList()) }
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }

        val feedObserver = Observer<List<PostModel>> {
            view.progressBar.visibility = View.GONE
            view.feed.adapter = PostAdapter(this, REQUEST_CODE, it)
            view.feed.adapter?.notifyDataSetChanged()
        }

        feedViewModel.posts.observe(viewLifecycleOwner, feedObserver)
    }

    private fun loadFeed(view: View) {
        view.progressBar.visibility = View.VISIBLE
        ServerUtils.loadFeed(feedViewModel.posts)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val position = data?.extras?.getInt(ExtraName.position)
            position?.let { view?.feed?.adapter?.notifyItemChanged(it) }
        }
    }

    data class FeedViewModel(val posts: MutableLiveData<List<PostModel>> = MutableLiveData()) : ViewModel()
}