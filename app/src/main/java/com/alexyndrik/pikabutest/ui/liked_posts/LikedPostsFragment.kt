package com.alexyndrik.pikabutest.ui.liked_posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alexyndrik.pikabutest.R

class LikedPostsFragment : Fragment() {

    private lateinit var likedPostsViewModel: LikedPostsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        likedPostsViewModel =
                ViewModelProviders.of(this).get(LikedPostsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_liked_posts, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        likedPostsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}