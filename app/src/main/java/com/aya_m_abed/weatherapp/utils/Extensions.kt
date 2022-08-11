package com.aya_m_abed.weatherapp.utils

import android.annotation.SuppressLint
import android.graphics.PorterDuff
import android.widget.ImageView
import com.aya_m_abed.weatherapp.R
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.ceil

fun Double.roundToInteger(): Int {
    return ceil(this).toInt()
}

fun ImageView.setWeatherIcon(weatherCode: Int, currentDayMode: String) {

    when {
        weatherCode / 100 == 2 -> {
            Glide.with(this).load(Constants.STORMY)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode / 100 == 3 -> {
            Glide.with(this).load(Constants.SHOWER_RAIN)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Day" && weatherCode / 100 == 5 -> {
            Glide.with(this).load(Constants.RAIN_DAY)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Night" && weatherCode / 100 == 5 -> {
            Glide.with(this).load(Constants.RAIN_NIGHT)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode / 100 == 6 -> {
            Glide.with(this).load(Constants.SNOW)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode / 100 == 7 -> {
            Glide.with(this).load(Constants.MIST)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Day" && weatherCode == 800 -> {
            Glide.with(this).load(Constants.SUN)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Night" && weatherCode == 800 -> {
            Glide.with(this).load(Constants.MOON)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Day" && weatherCode == 801 -> {
            Glide.with(this).load(Constants.SUN_CLOUD_DAY)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        currentDayMode == "Night" && weatherCode == 801 -> {
            Glide.with(this).load(Constants.SUN_CLOUD_NIGHT)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode == 802 -> {
            Glide.with(this).load(Constants.JUST_CLOUDY)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode == 803 -> {
            Glide.with(this).load(Constants.BROKEN_CLOUDS)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
        weatherCode == 804 -> {
            Glide.with(this).load(Constants.BROKEN_CLOUDS)
                .fitCenter()
                .placeholder(R.drawable.ic_load_icon)
                .error(R.drawable.ic_error_icon)
                .into(this)
        }
    }


}
fun String.capitalizeFirstLetterInEachWord(): String {
    return this.trim().split("\\s+".toRegex())
        .joinToString(" ") { s -> s.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } }
}


fun Double.convertPressureToPsiUnit(): Double = this * 0.0145038

fun Long.convertTimeValueToDateString() : String {
    return SimpleDateFormat("HH:mm", Locale.ENGLISH).format(this * 1000L)
}


fun String.formatDateTime(initialPattern: String, converterPattern: String): String {
    @SuppressLint("SimpleDateFormat")
    val parser = SimpleDateFormat(initialPattern)

    @SuppressLint("SimpleDateFormat")
    val formatter = SimpleDateFormat(converterPattern)
    return formatter.format(parser.parse(this) as Date)
}

fun String.subtractHours(): String {
    return if (this == "00:00") "21:00"
    else if (this.extractHour() <= 12) "0${(this.extractHour() - "03".toInt())}:00"
    else "${(this.extractHour() - "03".toInt())}:00"
}

fun String.extractHour(): Int = this.slice(0..1).toInt()

fun Double.getTemperatureRounded() = this.roundToInteger().toString()
