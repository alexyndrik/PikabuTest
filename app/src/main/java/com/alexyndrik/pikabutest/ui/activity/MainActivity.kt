package com.alexyndrik.pikabutest.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.common.Const
import com.alexyndrik.pikabutest.common.LikesProvider
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.model.PikabuResponse
import com.alexyndrik.pikabutest.ui.fragment.FeedFragment
import com.alexyndrik.pikabutest.ui.fragment.LikedPostsFragment
import com.alexyndrik.pikabutest.utils.FragmentUtils
import com.alexyndrik.pikabutest.utils.PikabuServerUtils
import com.alexyndrik.pikabutest.utils.VolleyUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var postsLiveData = MutableLiveData<PikabuResponse<ArrayList<PikabuPost>>>()
        var likedPostLiveData = MutableLiveData<PikabuResponse<Int>>()
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
        val postsObserver = Observer<PikabuResponse<ArrayList<PikabuPost>>> {
            progress_bar.visibility = View.GONE
        }
        postsLiveData.observe(this, postsObserver)
    }

    private fun loadPosts() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            postsLiveData.value = PikabuResponse(error = Exception(getString(R.string.no_internet)))
            return
        }

        progress_bar.visibility = View.VISIBLE
        PikabuServerUtils.loadFeed(postsLiveData)
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }
}