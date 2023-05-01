package com.example.hikingdom.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.LocationManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.location.LocationListener
import android.content.Context.LOCATION_SERVICE
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class LocationHelper {
    val LOCATION_REFRESH_TIME = 1000 // 3 seconds. The Minimum Time to get location update
    val LOCATION_REFRESH_DISTANCE = 5 // 30 meters. The Minimum Distance to be changed to get location update
    val MY_PERMISSIONS_REQUEST_LOCATION = 100

    var hikingLocationListener: HikingLocationListener? = null

    interface HikingLocationListener {
        fun onLocationChanged(location: Location)
    }

//    fun startListeningUserLocation(context: Context, hikingListener: HikingLocationListener) {
//        hikingLocationListener = hikingListener
//
//        val mLocationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
//
//        val mLocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                //your code here
//                hikingLocationListener!!.onLocationChanged(location) // calling listener to inform that updated location is available
//            }
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {}
//        }
//
//    }

    @SuppressLint("MissingPermission")
    fun startListeningUserLocation(context: Context, myListener: HikingLocationListener) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                myListener.onLocationChanged(location) // calling listener to inform that updated location is available
            }
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        }
        mLocationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            LOCATION_REFRESH_TIME.toLong(),
            LOCATION_REFRESH_DISTANCE.toFloat(),
            locationListener
        )
    }
}