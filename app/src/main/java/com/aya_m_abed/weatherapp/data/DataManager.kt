package com.aya_m_abed.weatherapp.data

import com.aya_m_abed.weatherapp.data.model.WeatherResponse
import com.aya_m_abed.weatherapp.utils.*
import java.util.*

class DataManager(dataResponse: WeatherResponse?) {
    private val currentDataResponse = dataResponse?.list?.get(0)
    private val commonDataResponse = dataResponse?.city
    val hourlyWeatherDataResponse = dataResponse?.list?.take(8)

    private val apiDateFormat = Constants.API_DATE_FORMAT
    private val dateFormat = Constants.YEAR_MONTH_DAY_FORMAT
    private val timezoneFormat = Constants.DATE_WITH_TIMEZONE_FORMAT
    private val timeFormat = Constants.HOUR_MINUTE_FORMAT

    private val currentDateFormatted = getCurrentDateTime().formatDateTime(timezoneFormat, dateFormat)
    val currentDayFormatted = getCurrentDateTime().formatDateTime(timezoneFormat, Constants.DAY_FORMAT)
    val currentTimeFormatted = getCurrentDateTime().formatDateTime(timezoneFormat, timeFormat)

    val dailyWeatherDataResponse = dataResponse?.list?.filter {
        it.dt_txt.formatDateTime(apiDateFormat, dateFormat) != currentDateFormatted &&
                it.dt_txt.formatDateTime(apiDateFormat, timeFormat) ==
                currentDataResponse?.dt_txt?.formatDateTime(apiDateFormat, timeFormat)?.
                subtractHours()
    }

    val currentTemperature = currentDataResponse?.main?.temp
    val minTemperature = currentDataResponse?.main?.temp_min
    val maxTemperature = currentDataResponse?.main?.temp_max
    val humidityValue = currentDataResponse?.main?.humidity
    val windSpeedValue = currentDataResponse?.wind?.speed
    val windDegreeValue = currentDataResponse?.wind?.deg
    val feelsLikeValueRounded = currentDataResponse?.main?.feels_like?.getTemperatureRounded()
    val cityName = commonDataResponse?.name
    val countryName = commonDataResponse?.country
    val seaLevelValue = currentDataResponse?.main?.sea_level
    private val weatherDescription = currentDataResponse?.weather?.get(0)?.description
    private val airPressure = currentDataResponse?.main?.pressure

    private val sunsetValue =commonDataResponse?.sunset
    private val sunriseValue =commonDataResponse?.sunrise
    private val weatherIdValue =currentDataResponse?.weather


    fun getWeatherDescriptionCapitalized() = weatherDescription?.capitalizeFirstLetterInEachWord()

    fun getSeaLevelPressure(): String {
        return seaLevelValue?.convertPressureToPsiUnit()?.roundToInteger().toString()
    }


    fun getAirPressure(): String {
        return airPressure?.convertPressureToPsiUnit()?.roundToInteger().toString()
    }



    fun getSunriseTime() = sunriseValue?.convertTimeValueToDateString()
    fun getSunsetTime() = sunsetValue?.convertTimeValueToDateString()
    fun getWeatherId() = weatherIdValue?.get(0)?.id
    

    private fun getCurrentDateTime(): String {
        return Calendar.getInstance().time.toString()
    }

    fun getCurrentDayMode(): String {
        return if (getSunsetTime()?.extractHour()!! < currentTimeFormatted.extractHour()) "Night"
            else "Day"

    }

}