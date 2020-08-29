package com.alexyndrik.pikabutest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var feedFragment: FeedFragment
    private lateinit var likedPostsFragment: LikedPostsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LikesProvider.restoreLikedPosts(this)

        feedFragment = FeedFragment()
        likedPostsFragment = LikedPostsFragment()

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_container, feedFragment)
                .add(R.id.root_container, likedPostsFragment)
                .commit()
        }

        init()
    }

    private fun init() {
        showFeed()

        nav_view.setOnNavigationItemSelectedListener  {
            var result = false
            when (it.itemId) {
                R.id.navigation_feed -> {
                    showFeed()
                    result = true
                }
                R.id.navigation_liked_posts -> {
                    showLikedPosts()
                    result = true
                }
            }
            result
        }
    }

    private fun showFeed() {
        supportFragmentManager
            .beginTransaction()
            .show(feedFragment)
            .hide(likedPostsFragment)
            .commit()
    }

    private fun showLikedPosts() {
        supportFragmentManager
            .beginTransaction()
            .hide(feedFragment)
            .show(likedPostsFragment)
            .commit()
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }
}