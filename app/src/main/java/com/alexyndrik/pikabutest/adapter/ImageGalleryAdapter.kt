package com.alexyndrik.pikabutest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alexyndrik.pikabutest.R
import com.squareup.picasso.Picasso

class ImageGalleryAdapter(
    private val context: Context,
    private val images: List<String>,
    private val isPost: Boolean
) : RecyclerView.Adapter<ImageGalleryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val photoView = inflater.inflate(R.layout.view_image, parent, false)
        return MyViewHolder(photoView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageView = holder.photoImageView
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

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        var photoImageView: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener(this)
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