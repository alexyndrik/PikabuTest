package com.alexyndrik.pikabutest.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FeedFragment()
            1 -> return LikedPostsFragment()
        }
        return Fragment()
    }
}