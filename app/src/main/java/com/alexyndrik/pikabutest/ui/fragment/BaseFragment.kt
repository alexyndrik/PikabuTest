package com.alexyndrik.pikabutest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.PikabuApiClient.Response
import com.alexyndrik.pikabutest.ui.PostAdapter
import com.alexyndrik.pikabutest.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_base.view.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_base, container, false)
        retainInstance = true

        initView(root)
        initObservers(root)

        return root
    }

    private fun initView(view: View) {
        view.feed.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = PostAdapter(ArrayList(), MainActivity.likedPostLiveData)
            addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        }
    }

    private fun initObservers(view: View) {
        val postsObserver = Observer<Response<Map<Int, PikabuPost>>> {
            show(view, it.data, it.error, ::doPostsObserver as (Any) -> Unit)
        }

        MainActivity.postsLiveData.observe(viewLifecycleOwner, postsObserver)

        val likedPostObserver = Observer<Response<Int>> {
            show(view, it.data, it.error, ::doLikedPostObserver as (Any) -> Unit)
        }

        MainActivity.likedPostLiveData.observe(viewLifecycleOwner, likedPostObserver)
    }

    private fun show(view: View, data: Any?, error: Exception?, unit: (data: Any) -> Unit) {
        when {
            error != null -> showMessage(view, error.message)
            data != null -> {
                showFeed(view)
                unit(data)
            }
            else -> showMessage(view, null)
        }
    }

    private fun showFeed(view: View) {
        view.feed.visibility = View.VISIBLE
        view.message.visibility = View.GONE
    }

    fun showMessage(view: View, message: String?) {
        view.feed.visibility = View.GONE
        view.message.visibility = View.VISIBLE

        view.message.text = message ?: getString(R.string.stub_error)
    }

    abstract fun doPostsObserver(posts: Map<Int, PikabuPost>)
    abstract fun doLikedPostObserver(id: Int)

}