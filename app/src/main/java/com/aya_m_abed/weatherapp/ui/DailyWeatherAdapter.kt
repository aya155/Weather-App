package com.aya_m_abed.weatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aya_m_abed.weatherapp.R
import com.aya_m_abed.weatherapp.data.model.Feed
import com.aya_m_abed.weatherapp.ui.viewHolders.DailyWeatherViewHolder
import com.aya_m_abed.weatherapp.utils.Constants
import com.aya_m_abed.weatherapp.utils.formatDateTime
import com.aya_m_abed.weatherapp.utils.getTemperatureRounded
import com.aya_m_abed.weatherapp.utils.setWeatherIcon

class DailyWeatherAdapter(
    private val dailyWeatherList: List<Feed>,
    private val currentDayMode: String,
    private val degreeUnit: String
): RecyclerView.Adapter<DailyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_weather_item, parent, false)
        return DailyWeatherViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        val currentDailyWeather = dailyWeatherList[position]
        holder.apply {
            day.text = currentDailyWeather.dt_txt.formatDateTime(Constants.API_DATE_FORMAT, Constants.DAY_FORMAT)
            date.text = currentDailyWeather.dt_txt.formatDateTime(Constants.API_DATE_FORMAT, Constants.MONTH_DAY_FORMAT)
            icon.setWeatherIcon(currentDailyWeather.weather[0].id, currentDayMode)
            degree.text = "${currentDailyWeather.main.temp.getTemperatureRounded()}${degreeUnit}"
        }

    }

    override fun getItemCount(): Int = dailyWeatherList.size
}