package com.alexyndrik.pikabutest.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alexyndrik.pikabutest.Const
import com.alexyndrik.pikabutest.R
import com.alexyndrik.pikabutest.model.PostModel
import com.alexyndrik.pikabutest.utils.LikesProvider
import com.alexyndrik.pikabutest.utils.PostUtils
import com.alexyndrik.pikabutest.utils.ServerUtils
import kotlinx.android.synthetic.main.view_post.*


class PostActivity : AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_post)

        postViewModel = PostViewModel()
        val id = intent.getIntExtra(Const.POST_ID, -1)
//        if (id == -1)

        init()
        loadPost(id)
    }

    private fun init() {
        post_view.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        val observer = Observer<PostModel> {
            progress_bar.visibility = View.GONE
            PostUtils.fillPostInfo(post_view, it, true, MainActivity.likedPost)
        }
        postViewModel.post.observe(this, observer)
    }

    private fun loadPost(id: Int) {
        progress_bar.visibility = View.VISIBLE
        ServerUtils.loadPost(postViewModel.post, id)
    }

    override fun onPause() {
        LikesProvider.saveLikedPosts(this)
        super.onPause()
    }

    override fun onBackPressed() {
        val returnIntent = Intent()
        returnIntent.putExtra(Const.POSITION, intent.getIntExtra(Const.POSITION, -1))
        setResult(RESULT_OK, returnIntent)
        super.finish()
    }

    data class PostViewModel(val post: MutableLiveData<PostModel> = MutableLiveData()) : ViewModel()

}