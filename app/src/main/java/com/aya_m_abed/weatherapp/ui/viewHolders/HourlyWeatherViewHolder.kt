package com.aya_m_abed.weatherapp.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aya_m_abed.weatherapp.R

class HourlyWeatherViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem) {
    val icon: ImageView = viewItem.findViewById(R.id.hourly_weather_icon)
    val degree: TextView = viewItem.findViewById(R.id.hourly_weather_degree)
    val hour: TextView = viewItem.findViewById(R.id.hour)

}