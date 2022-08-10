package com.aya_m_abed.weatherapp.data.model

data class Feed (
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val dt_txt: String,
    val pop: Int,
    val sys: Sys,

    )