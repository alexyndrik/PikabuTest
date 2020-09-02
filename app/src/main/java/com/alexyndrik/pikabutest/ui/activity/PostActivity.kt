package com.alexyndrik.pikabutest.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.service.LikesProvider
import com.alexyndrik.pikabutest.service.PikabuApiClient
import com.alexyndrik.pikabutest.service.PikabuApiClient.Response
import com.alexyndrik.pikabutest.ui.PostPresenter
import com.alexyndrik.pikabutest.ui.livedata.PostLiveData
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.view_post.*
import kotlin.properties.Delegates


class PostActivity : AppCompatActivity() {

    private var id by Delegates.notNull<Int>()
    private lateinit var queue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        queue = Volley.newRequestQueue(this)

        initView()
        initObservers()

        id = intent.getIntExtra(Const.ExtraName.POST_ID, -1)
        if (id == -1)
            showError(null)
        else
            loadPost()
    }

    private fun initView() {
        if (Build.VERSION.SDK_INT < 23) {
            post_title.setTextAppearance(this, R.style.Title)
            post_body.setTextAppearance(this, R.style.Text)
        } else {
            post_title.setTextAppearance(R.style.Title)
            post_body.setTextAppearance(R.style.Text)
        }
    }

    private fun initObservers() {
        val observer = Observer<Response<PikabuPost>> {
            progress_bar.visibility = View.GONE
            if (it.data != null)
                PostPresenter.fillPostInfo(post_view, it.data, false)
            else
                showError(it.error?.message)
        }
        PostLiveData.observe(this, observer)
    }

    private fun loadPost() {
        progress_bar.visibility = View.VISIBLE
        PikabuApiClient.loadPost(queue, id)
    }

    private fun showError(errorMessage: String?) {
        post_view.visibility = View.GONE
        message.visibility = View.VISIBLE

        message.text = errorMessage ?: getString(R.string.stub_error)
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra(Const.ExtraName.POST_ID, id)
        setResult(RESULT_OK, returnIntent)
        super.finish()
    }

}