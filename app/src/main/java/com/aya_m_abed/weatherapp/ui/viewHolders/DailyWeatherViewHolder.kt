package com.aya_m_abed.weatherapp.ui.viewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aya_m_abed.weatherapp.R

class DailyWeatherViewHolder(viewItem: View): RecyclerView.ViewHolder(viewItem)  {
    val day: TextView = viewItem.findViewById(R.id.day)
    val date: TextView = viewItem.findViewById(R.id.date)
    val icon: ImageView = viewItem.findViewById(R.id.icon)
    val degree: TextView = viewItem.findViewById(R.id.degree)
}