package com.aya_m_abed.weatherapp.utils

import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.aya_m_abed.weatherapp.ui.MainActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task


fun turnOnGPS(locationRequest: LocationRequest?, context: MainActivity) {
    val builder = locationRequest?.let {
        LocationSettingsRequest.Builder()
            .addLocationRequest(it)
    }
    builder?.setAlwaysShow(true)
    val result: Task<LocationSettingsResponse> = builder?.let {
        LocationServices.getSettingsClient(
            context
        )
            .checkLocationSettings(it.build())
    } as Task<LocationSettingsResponse>
    result.addOnCompleteListener { task ->
        try {
            task.getResult(ApiException::class.java)
            Toast.makeText(context, "GPS is already tured on", Toast.LENGTH_SHORT)
                .show()
        } catch (e: ApiException) {
            when (e.statusCode) {
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    val resolvableApiException = e as ResolvableApiException
                    resolvableApiException.startResolutionForResult(context, 2)
                } catch (ex: IntentSender.SendIntentException) {
                    ex.printStackTrace()
                }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
            }
        }
    }
}
