package com.aya_m_abed.weatherapp.data.model
import java.math.BigInteger

data class City (
    val id: Int,
    val name: String,
    val coord: Coordinate,
    val country: String,
    val population: BigInteger,
    val timezone: Long,
    val sunset: Long,
    val sunrise: Long,
    )
