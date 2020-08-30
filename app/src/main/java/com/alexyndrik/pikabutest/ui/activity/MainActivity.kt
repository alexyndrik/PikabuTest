package com.alexyndrik.pikabutest.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment
import com.alexyndrik.pikabutest.utils.FragmentUtils
import com.alexyndrik.pikabutest.utils.LikesProvider
import com.alexyndrik.pikabutest.utils.PikabuServerUtils
import com.alexyndrik.pikabutest.utils.VolleyUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var postsLiveData = MutableLiveData<ArrayList<PostModel>>()
        var likedPostLiveData = MutableLiveData<Int>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        VolleyUtils.init(this)
        LikesProvider.restoreLikedPosts(this)

        if (savedInstanceState == null) {
            val feedFragment = FragmentUtils.addFragment(supportFragmentManager, FeedFragment(), Const.FEED_FRAGMENT)
            val likedPostsFragment = FragmentUtils.addFragment(supportFragmentManager, LikedPostsFragment(), Const.LIKED_POSTS_FRAGMENT)

            FragmentUtils.showFragment(supportFragmentManager, feedFragment, likedPostsFragment)
        }

        initView()
        initObservers()

        loadPosts()
    }

    private fun initView() {
        bottom_navigation.setOnNavigationItemSelectedListener  {
            FragmentUtils.showFragmentById(supportFragmentManager, it.itemId)
        }
    }

    private fun initObservers() {
        val postsObserver = Observer<List<PostModel>> {
            progress_bar.visibility = View.GONE
        }
        postsLiveData.observe(this, postsObserver)
    }

    private fun loadPosts() {
        println("main activity load posts")
        progress_bar.visibility = View.VISIBLE
//        PikabuServerUtils.loadFeed(postsLiveData)
        PikabuServerUtils.loadFeed(postsLiveData)
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }
}