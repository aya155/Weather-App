package com.aya_m_abed.weatherapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aya_m_abed.weatherapp.R
import com.aya_m_abed.weatherapp.data.model.Feed
import com.aya_m_abed.weatherapp.ui.viewHolders.HourlyWeatherViewHolder
import com.aya_m_abed.weatherapp.utils.Constants
import com.aya_m_abed.weatherapp.utils.formatDateTime
import com.aya_m_abed.weatherapp.utils.getTemperatureRounded
import com.aya_m_abed.weatherapp.utils.setWeatherIcon

class HourlyWeatherAdapter(
    private val hourlyWeatherList: List<Feed>,
    private val currentDayMode: String,
    private val degreeUnit: String
): RecyclerView.Adapter<HourlyWeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyWeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_card, parent, false)
        return HourlyWeatherViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HourlyWeatherViewHolder, position: Int) {
        val currentHourlyWeather = hourlyWeatherList[position]
        holder.apply {
            degree.text = "${currentHourlyWeather.main.temp.getTemperatureRounded()}${degreeUnit}"
            icon.setWeatherIcon(currentHourlyWeather.weather[0].id, currentDayMode)
            hour.text = currentHourlyWeather.dt_txt.formatDateTime(Constants.API_DATE_FORMAT, Constants.HOUR_MINUTE_FORMAT)
        }
    }

    override fun getItemCount(): Int = hourlyWeatherList.size
}