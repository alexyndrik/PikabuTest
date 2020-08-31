package com.alexyndrik.pikabutest.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.LikesProvider
import com.alexyndrik.pikabutest.service.PikabuApiClient
import com.alexyndrik.pikabutest.service.PikabuApiClient.Response
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue

    companion object {
        var postsLiveData = MutableLiveData<Response<ArrayList<PikabuPost>>>()
        var likedPostLiveData = MutableLiveData<Response<Int>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        LikesProvider.restoreLikedPosts(this)

        if (savedInstanceState == null) {
            val feedFragment = addFragment(supportFragmentManager, FeedFragment(), Const.FragmentTag.FEED_FRAGMENT)
            val likedPostsFragment = addFragment(supportFragmentManager, LikedPostsFragment(), Const.FragmentTag.LIKED_POSTS_FRAGMENT)

            switchFragment(supportFragmentManager, feedFragment, likedPostsFragment)
        }

        initView()
        initObservers()

        loadPosts()
    }

    private fun initView() {
        bottom_navigation.setOnNavigationItemSelectedListener  {
            showFragmentById(supportFragmentManager, it.itemId)
        }
    }

    private fun initObservers() {
        val postsObserver = Observer<Response<ArrayList<PikabuPost>>> {
            progress_bar.visibility = View.GONE
        }
        postsLiveData.observe(this, postsObserver)
    }

    private fun loadPosts() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            postsLiveData.value = Response(error = Exception(getString(R.string.no_internet)))
            return
        }

        progress_bar.visibility = View.VISIBLE
        PikabuApiClient.loadFeed(queue, postsLiveData)
    }

    private fun showFragmentById(fragmentManager: FragmentManager, id: Int): Boolean {
        val feedFragment = fragmentManager.findFragmentByTag(Const.FragmentTag.FEED_FRAGMENT)
            ?: addFragment(fragmentManager, FeedFragment(), Const.FragmentTag.FEED_FRAGMENT)
        val likedPostsFragment = fragmentManager.findFragmentByTag(Const.FragmentTag.LIKED_POSTS_FRAGMENT)
            ?: addFragment(fragmentManager, LikedPostsFragment(), Const.FragmentTag.LIKED_POSTS_FRAGMENT)

        when (id) {
            R.id.navigation_feed -> {
                switchFragment(fragmentManager, feedFragment, likedPostsFragment)
                return true
            }
            R.id.navigation_liked_posts -> {
                switchFragment(fragmentManager, likedPostsFragment, feedFragment)
                return true
            }
        }

        return false
    }

    private fun addFragment(fragmentManager: FragmentManager, fragment: Fragment, tag: String): Fragment {
        fragmentManager
            .beginTransaction()
            .add(R.id.root_container, fragment, tag)
            .commit()
        return fragment
    }

    private fun switchFragment(fragmentManager: FragmentManager, show: Fragment, hide: Fragment) {
        fragmentManager
            .beginTransaction()
            .show(show)
            .hide(hide)
            .commit()
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }
}