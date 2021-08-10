package com.musicdemo.ui.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musicdemo.databinding.ItemLocationBinding
import com.musicdemo.model.apientities.Weather


class WeatherAdapter(private val dataSet: MutableList<Weather>) :
    RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


     val TAG: String = "WeatherAdapter"

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class ViewHolder(var view: ItemLocationBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            Log.d(TAG, "inside view holder init: ")
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")

        // Create a new view, which defines the UI of the list item
        val view =
            ItemLocationBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")
        viewHolder.view.weather = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() : Int{
        Log.d(TAG, "getItemCount: ")
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType: ")
        return super.getItemViewType(position)
    }

}