package com.alexyndrik.pikabutest.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.common.Const
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment

object FragmentUtils {

    fun showFragmentById(fragmentManager: FragmentManager, id: Int): Boolean {
        val feedFragment = fragmentManager.findFragmentByTag(Const.FEED_FRAGMENT)
            ?: addFragment(fragmentManager, FeedFragment(), Const.FEED_FRAGMENT)
        val likedPostsFragment = fragmentManager.findFragmentByTag(Const.LIKED_POSTS_FRAGMENT)
            ?: addFragment(fragmentManager, LikedPostsFragment(), Const.LIKED_POSTS_FRAGMENT)

        when (id) {
            R.id.navigation_feed -> {
                showFragment(fragmentManager, feedFragment, likedPostsFragment)
                return true
            }
            R.id.navigation_liked_posts -> {
                showFragment(fragmentManager, likedPostsFragment, feedFragment)
                return true
            }
        }

        return false
    }

    fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, tag: String): Fragment {
        fragmentManager
            .beginTransaction()
            .add(R.id.root_container, fragment, tag)
            .commit()
        return fragment
    }

    fun showFragment(fragmentManager: FragmentManager, show: Fragment, hide: Fragment) {
        fragmentManager
            .beginTransaction()
            .show(show)
            .hide(hide)
            .commit()
    }

}