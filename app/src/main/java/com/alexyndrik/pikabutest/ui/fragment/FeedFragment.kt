package com.alexyndrik.pikabutest.ui.fragment

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
import com.alexyndrik.pikabutest.PostModel
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.adapter.PostAdapter
import com.alexyndrik.pikabutest.utils.ServerUtils
import kotlinx.android.synthetic.main.fragment_feed.view.*

class FeedFragment : Fragment() {

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
            adapter = PostAdapter(requireContext(), ArrayList(), false)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }

        val observer = Observer<List<PostModel>> {
            view.progressBar.visibility = View.GONE
            view.feed.adapter = PostAdapter(requireContext(), it, false)
            view.feed.adapter?.notifyDataSetChanged()
        }

        feedViewModel.posts.observe(viewLifecycleOwner, observer)
    }

    private fun loadFeed(view: View) {
        view.progressBar.visibility = View.VISIBLE
        ServerUtils.loadFeed(feedViewModel.posts)
    }

    data class FeedViewModel(val posts : MutableLiveData<List<PostModel>> = MutableLiveData()) : ViewModel()
}