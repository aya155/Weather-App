package com.aya_m_abed.weatherapp.data.model

data class Main (
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Double,
    val sea_level: Double,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
)
