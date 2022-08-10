package com.aya_m_abed.weatherapp.data.request

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

class Client {
    private val client = OkHttpClient()

    fun makeApiRequest(latitude: Double, longitude: Double, city: String, tempUnit: String): Call {
        val request = Request.Builder()
            .url(makeApiUrl(latitude = latitude, longitude = longitude, city = city, temperatureMeasurement = tempUnit))
            .build()

        return client.newCall(request)
    }
}