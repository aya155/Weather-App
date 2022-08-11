package com.aya_m_abed.weatherapp.ui


import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.aya_m_abed.weatherapp.data.DataManager
import com.aya_m_abed.weatherapp.data.model.WeatherResponse
import com.aya_m_abed.weatherapp.data.request.Client
import com.aya_m_abed.weatherapp.databinding.ActivityMainBinding
import com.aya_m_abed.weatherapp.utils.*
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {
    val LOG_TAG: String = "MAIN_ACTIVITY"

    private lateinit var binding: ActivityMainBinding
    private val client by lazy { Client() }
    private var locationRequest: LocationRequest? = null
    lateinit var dataManager: DataManager
    lateinit var dialog: Dialog
    private var selectedDegree = Constants.METRIC
    private var degreeUnit = Constants.CELSIUS_UNIT

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        installSplashScreen()

        supportActionBar?.apply{hide()}

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 5000
        locationRequest!!.fastestInterval = 2000

        dialog = setProgressDialog(this, "Loading..")
        dialog.show()

        getCurrentLocation()
    }

    private fun makeRequest(latitude: Double, longitude: Double, tempUnit: String, city: String) {

        client.makeApiRequest(latitude = latitude, longitude = longitude, tempUnit = tempUnit, city = city)
            .enqueue(object : Callback{
            @SuppressLint("ClickableViewAccessibility")
            override fun onFailure(call: Call, e: IOException) {
                failureRequest()
            }
            @SuppressLint("ClickableViewAccessibility")
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                    runOnUiThread {
                        if (result.cod != 200)
                            failureRequest()
                        else {
                            dataManager = DataManager(result)
                            representData()
                        }

                    }
                }
            }

        })
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun failureRequest() {
        binding.apply {
            scrollView.setOnTouchListener { _, _ -> true }
            allContent.visibility = View.VISIBLE
            centerContent.visibility = View.INVISIBLE
            notFoundAnimation.apply {
                visibility = View.VISIBLE
                playAnimation()
                loop(true)
            }
            dialog.dismiss()
        }
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    private fun representData() {
        searchForCity()

        val dailyWeatherAdapter = dataManager.dailyWeatherDataResponse?.let {
            DailyWeatherAdapter(
                it, dataManager.getCurrentDayMode(), degreeUnit
            )
        }
        val hourlyWeatherAdapter = dataManager.hourlyWeatherDataResponse?.let {
            HourlyWeatherAdapter(
                it, dataManager.getCurrentDayMode(), degreeUnit
            )
        }

        binding.apply {

            hourlyWeatherCard.adapter = hourlyWeatherAdapter
            dailyWeatherCard.adapter = dailyWeatherAdapter
            scrollView.setOnTouchListener { _, _ -> false }

            windSpeedValue.text = if ( selectedDegree == Constants.METRIC )
                "${dataManager.windSpeedValue}m/s" else
                    "${dataManager.windSpeedValue}mi/h"

            windDegreeValue.text = "${dataManager.windDegreeValue}°"
            feelsLikeValue.text = "${dataManager.feelsLikeValueRounded}${degreeUnit}"
            progressBarValue.text = "${dataManager.humidityValue!!}%"

            progressBar.max = 100
            progressBar.progress = dataManager.humidityValue!!

            notFoundAnimation.visibility = View.INVISIBLE
            allContent.visibility = View.VISIBLE
            centerContent.visibility = View.VISIBLE

            time.text = dataManager.currentTimeFormatted
            day.text = dataManager.currentDayFormatted
            weatherDegree.text = "${dataManager.currentTemperature?.getTemperatureRounded()}${degreeUnit}"
            minDegree.text = "${dataManager.minTemperature?.getTemperatureRounded()}${degreeUnit}"
            maxDegree.text = "${dataManager.maxTemperature?.getTemperatureRounded()}${degreeUnit}"
            locationText.text = "${dataManager.cityName} ,${dataManager.countryName}"
            sunriseTime.text = dataManager.getSunriseTime()
            sunsetTime.text = dataManager.getSunsetTime()
            weatherDescription.text = dataManager.getWeatherDescriptionCapitalized()

            pressureValue.text = "${dataManager.getAirPressure()} psi"
            seaLevelValue.text = "${dataManager.getSeaLevelPressure()} psi"

            dataManager.getWeatherId()?.let { weatherIcon.setWeatherIcon(it, dataManager.getCurrentDayMode()) }

            settingsIcon.setOnClickListener {
                showUnitsDialog(this@MainActivity)

            }
            dailyWeatherCard.isNestedScrollingEnabled = false;
            allContent.visibility = View.VISIBLE

            dialog.dismiss()

            windAnimation.playAnimation()
            windAnimation.loop(true)

        }
    }

    private fun searchForCity() {
        binding.apply {
            searchField.setOnQueryTextFocusChangeListener { _ , hasFocus ->
                if (hasFocus) {
                    searchField.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            searchField.clearFocus()
                            makeRequest(latitude = 0.0, longitude = 0.0, selectedDegree, city = query.toString())
                            return true
                        }
                        override fun onQueryTextChange(newText: String?): Boolean {
                            return false
                        }

                    })
                }
            }
        }
    }

    private fun showUnitsDialog(context: MainActivity) {
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
        alertDialog.setTitle("Choose Degree")
        val items = arrayOf("Fahrenheit", "Celsius")
        val checkedItem = if (selectedDegree == Constants.METRIC) 1 else 0
        alertDialog.setSingleChoiceItems(items, checkedItem) { _, which ->
            when (which) {
                0 -> {
                    selectedDegree = Constants.IMPERIAL
                    degreeUnit = Constants.FAHRENHEIT_UNIT
                    getCurrentLocation()
                }
                1 -> {
                    selectedDegree = Constants.METRIC
                    degreeUnit = Constants.CELSIUS_UNIT
                    getCurrentLocation()
                }
            }
        }
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()

    }

    private fun getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (isGPSEnabled()) {
                    locationRequest?.let {
                        LocationServices.getFusedLocationProviderClient(this@MainActivity)
                            .requestLocationUpdates(it, object : LocationCallback() {
                                override fun onLocationResult(locationResult: LocationResult) {
                                    super.onLocationResult(locationResult)
                                    LocationServices.getFusedLocationProviderClient(this@MainActivity)
                                        .removeLocationUpdates(this)
                                    if (locationResult.locations.size > 0) {
                                        val index = locationResult.locations.size - 1
                                        val latitude = locationResult.locations[index].latitude
                                        val longitude = locationResult.locations[index].longitude
                                        makeRequest(latitude, longitude, selectedDegree, "")
                                    }
                                }
                            }, Looper.getMainLooper())
                    }
                } else {
                    turnOnGPS(locationRequest, this)
                }
            } else {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }
        }
    }

    private fun isGPSEnabled(): Boolean {
        var locationManager: LocationManager? = null
        if (locationManager == null) {
            locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    getCurrentLocation()
                } else {
                    turnOnGPS(locationRequest, this@MainActivity)
                    getCurrentLocation()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                getCurrentLocation()
            }
        }
    }

}