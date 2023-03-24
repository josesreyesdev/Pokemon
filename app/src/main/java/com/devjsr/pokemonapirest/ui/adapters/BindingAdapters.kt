package com.devjsr.pokemonapirest.ui.adapters

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.devjsr.pokemonapirest.R

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?) {

    imgUrl?.let {
        //Convert una string de url en un objeto Uri
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        //load image
        imageView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

