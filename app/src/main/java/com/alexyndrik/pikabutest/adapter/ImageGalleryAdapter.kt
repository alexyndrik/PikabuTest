package com.alexyndrik.pikabutest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_image.view.*

class ImageGalleryAdapter(
    private val context: Context,
    private val images: List<String>,
    private val isPost: Boolean
) : RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val imageView = LayoutInflater.from(context).inflate(R.layout.view_image, parent, false)
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageView = holder.itemView.image
//        val imageView = ImageView(context)
        imageView.scaleType = ImageView.ScaleType.FIT_START
        imageView.adjustViewBounds = true

        val picasso = Picasso.get()
            .load(images[position])
            .tag(context)
        if (!isPost)
            picasso.resize(0, 100)
        picasso.into(imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        init {
            itemView.image.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
//                val intent = Intent(context, SunsetPhotoActivity::class.java).apply {
//                    putExtra(SunsetPhotoActivity.EXTRA_SUNSET_PHOTO, images[position])
//                }
//                startActivity(intent)
            }
        }
    }
}