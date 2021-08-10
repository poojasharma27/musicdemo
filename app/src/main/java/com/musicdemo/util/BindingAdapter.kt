package com.musicdemo.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdemo.model.apientities.Weather
import com.musicdemo.ui.weather.WeatherAdapter


@BindingAdapter("url")
fun urlToImage(view: ImageView, url: String?){
    url?.apply {
            Glide.with(view).load("http:$url").centerCrop().into(view)
    }
}

@BindingAdapter("weatherList")
fun weatherList(view: RecyclerView, list : ObservableArrayList<Weather>){

   view.adapter?.apply {
       notifyDataSetChanged()
   }?: run {
       view.adapter = WeatherAdapter(list)
   }

}