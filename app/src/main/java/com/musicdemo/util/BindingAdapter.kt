package com.musicdemo.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("url")
fun urlToImage(view: ImageView, url: String?){
    url?.apply {
            Glide.with(view).load("http:$url").centerCrop().into(view)
    }
}