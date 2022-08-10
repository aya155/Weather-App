package com.aya_m_abed.weatherapp.data.request

import com.aya_m_abed.weatherapp.utils.Constants
import okhttp3.HttpUrl

fun makeApiUrl(
    latitude: Double,
    longitude: Double,
    temperatureMeasurement: String,
    city: String
): HttpUrl {

    return if (city.isEmpty()) HttpUrl.Builder()
        .scheme(Constants.SCHEMA)
        .host(Constants.HOST)
        .addPathSegment(Constants.PATH)
        .addQueryParameter(Constants.GEO_COORDINATE_LATITUDE, latitude.toString())
        .addQueryParameter(Constants.GEO_COORDINATE_LONGITUDE, longitude.toString())
        .addQueryParameter(Constants.CNT, "40")
        .addQueryParameter(Constants.UNITS, temperatureMeasurement)
        .addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY_VALUE)
        .build()

    else HttpUrl.Builder()
        .scheme(Constants.SCHEMA)
        .host(Constants.HOST)
        .addPathSegment(Constants.PATH)
        .addQueryParameter(Constants.CITY_PARAMETER, city)
        .addQueryParameter(Constants.CNT, "40")
        .addQueryParameter(Constants.UNITS, temperatureMeasurement)
        .addQueryParameter(Constants.API_KEY_QUERY, Constants.API_KEY_VALUE)
        .build()
}
