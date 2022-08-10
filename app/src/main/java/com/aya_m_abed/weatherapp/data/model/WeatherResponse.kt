package com.aya_m_abed.weatherapp.data.model

data class WeatherResponse(
    val cod: Int,
    val message: String,
    val cnt: Int,
    val list: List<Feed>,
    val city: City
)
