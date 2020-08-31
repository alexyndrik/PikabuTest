package com.alexyndrik.pikabutest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.common.Const
import com.alexyndrik.pikabutest.common.LikesProvider
import com.alexyndrik.pikabutest.model.PikabuPost
import com.alexyndrik.pikabutest.model.PikabuResponse
import com.alexyndrik.pikabutest.utils.PikabuServerUtils
import com.alexyndrik.pikabutest.utils.PostUtils
import kotlinx.android.synthetic.main.view_post.*


class PostActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_post)

        postViewModel = PostViewModel()
        val id = intent.getIntExtra(Const.POST_ID, -1)
        if (id == -1)
            showError(null)

        init()
        loadPost(id)
    }

    private fun init() {
        post_view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val observer = Observer<PikabuResponse<PikabuPost>> {
            progress_bar.visibility = View.GONE
            if (it.response != null)
                PostUtils.fillPostInfo(post_view, it.response, true, MainActivity.likedPostLiveData)
            else
                showError(it.error?.message)
        }
        postViewModel.post.observe(this, observer)
    }

    private fun loadPost(id: Int) {
        progress_bar.visibility = View.VISIBLE
        PikabuServerUtils.loadPost(postViewModel.post, id)
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
        returnIntent.putExtra(Const.POST_ID, intent.getIntExtra(Const.POST_ID, -1))
        setResult(RESULT_OK, returnIntent)
        super.finish()
    }

    data class PostViewModel(val post: MutableLiveData<PikabuResponse<PikabuPost>> = MutableLiveData()) : ViewModel()

}