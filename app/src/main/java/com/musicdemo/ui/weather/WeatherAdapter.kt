package com.musicdemo.ui.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musicdemo.databinding.ItemFooterBinding
import com.musicdemo.databinding.ItemLocationBinding
import com.musicdemo.databinding.ItemTextBinding
import com.musicdemo.listeners.OnRecyclerViewItemClickListener
import com.musicdemo.model.apientities.Weather


class WeatherAdapter(private val dataSet: MutableList<Weather>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var listener: OnRecyclerViewItemClickListener

    fun setItemClickListener(listener: OnRecyclerViewItemClickListener) {
        this.listener = listener
    }

    val TAG: String = "WeatherAdapter"

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    inner class WeatherViewHolder(var view: ItemLocationBinding) :
        RecyclerView.ViewHolder(view.root) {
        init {
            Log.d(TAG, "inside view holder init: ")
        }
    }

    inner class TextViewHolder(var view: ItemTextBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            Log.d(TAG, "inside view holder init: ")
        }
    }

    inner class FooterViewHolder(var view: ItemFooterBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            Log.d(TAG, "inside view holder init: ")
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ")


        val viewHolder: RecyclerView.ViewHolder = WeatherViewHolder(
            ItemLocationBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
        return when (viewType) {
            viewTypeText -> {
                TextViewHolder(
                    ItemTextBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false
                    )
                )
            }
            viewTypeFooter -> {
                FooterViewHolder(
                    ItemFooterBinding.inflate(
                        LayoutInflater.from(viewGroup.context),
                        viewGroup,
                        false
                    )
                )
            }
            else -> {
                viewHolder
            }
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: ")


        if (dataSet.size > 0 && position<dataSet.size) {
            when (viewHolder) {
                is WeatherViewHolder -> {
                    viewHolder.view.weather = dataSet[position]
                }
                is TextViewHolder -> {
                    //viewHolder.view.weather = dataSet[position]
                }
                is FooterViewHolder -> {
                    //viewHolder.view.weather = dataSet[position]
                }

            }

            viewHolder.itemView.setOnClickListener {
                listener.onItemClick(it, viewHolder.adapterPosition)
            }
        }

    }
    

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount: ${dataSet.size} ")
        return if (dataSet.size == 0) {
            0
        } else {
            dataSet.size  + 1
        }

    }

    val viewTypeText = 1
    val viewTypeWeather = 2
    val viewTypeFooter = 3
    val viewtypeFood = 4
    val viewtypeNews = 5

    override fun getItemViewType(position: Int): Int {
        Log.d(TAG, "getItemViewType: ")
        return if (position == dataSet.size) {
                viewTypeFooter
            } else {
                viewTypeWeather
        }
    }

}