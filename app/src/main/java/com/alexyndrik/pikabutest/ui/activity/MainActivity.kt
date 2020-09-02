package com.alexyndrik.pikabutest.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.LikesProvider
import com.alexyndrik.pikabutest.service.PikabuApiClient
import com.alexyndrik.pikabutest.ui.FragmentAdapter
import com.alexyndrik.pikabutest.ui.livedata.AllPostsLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)
        LikesProvider.restoreLikedPosts(this)

        initView()
        initObservers()

        loadAllPosts(true)
    }

    private fun initView() {
        root_container.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        root_container.adapter = FragmentAdapter(supportFragmentManager, lifecycle)
        root_container.currentItem = 0

        root_container.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> bottom_navigation.menu.findItem(R.id.navigation_feed).isChecked = true
                    1 -> bottom_navigation.menu.findItem(R.id.navigation_liked_posts).isChecked = true
                }
            }
        })

        bottom_navigation.setOnNavigationItemSelectedListener  {
            var result = false
            when (it.itemId) {
                R.id.navigation_feed -> {
                    root_container.currentItem = 0
                    result = true
                }
                R.id.navigation_liked_posts -> {
                    root_container.currentItem = 1
                    result = true
                }
            }
            result
        }

        swipe_fragment.setOnRefreshListener {
            val runnable = Runnable {
                swipe_fragment.isRefreshing = false
                loadAllPosts(false)
            }
            Handler().postDelayed(runnable, 1000.toLong())
        }
    }

    private fun initObservers() {
        val postsObserver = Observer<PikabuApiClient.Response<Map<Int, PikabuPost>>> {
            progress_bar.visibility = View.GONE
        }
        AllPostsLiveData.observe(this, postsObserver)
    }

    private fun loadAllPosts(showProgressBar: Boolean) {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected) {
            AllPostsLiveData.value = PikabuApiClient.Response(error = Exception(getString(R.string.no_internet)))
            return
        }

        if (showProgressBar) progress_bar.visibility = View.VISIBLE
        PikabuApiClient.loadAllPosts(queue)
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }
}