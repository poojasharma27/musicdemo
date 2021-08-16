package com.musicdemo.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdemo.listeners.OnRecyclerViewItemClickListener
import com.musicdemo.model.apientities.Weather
import com.musicdemo.ui.weather.WeatherAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@BindingAdapter("url")
fun urlToImage(view: ImageView, url: String?) {
    url?.apply {
        Glide.with(view).load("http:$url").centerCrop().into(view)
    }
}


val weatherItemClickedTrigger = MutableStateFlow<Int>(-1)

@BindingAdapter("weatherList")
fun weatherList(view: RecyclerView, list: ObservableArrayList<Weather>) {

    view.adapter?.apply {
        notifyDataSetChanged()
    } ?: run {
        view.adapter = WeatherAdapter(list)
    }

    (view.adapter as? WeatherAdapter)?.setItemClickListener(object : OnRecyclerViewItemClickListener{
        override fun onItemClick(view: View, position: Int) {
            weatherItemClickedTrigger.value = position
        }
    })

}