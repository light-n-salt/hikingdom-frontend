package com.example.hikingdom.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.location.LocationListener
import android.location.Location
import com.example.hikingdom.BuildConfig
import com.example.hikingdom.ui.main.hiking.HikingFragment

class LocationHelper {
    // 2000ms, 15미터로 테스트했을 때 적합
    val LOCATION_REFRESH_TIME = 2000 // 3 seconds. The Minimum Time to get location update
    val LOCATION_REFRESH_DISTANCE = 10 // 30 meters. The Minimum Distance to be changed to get location update
    val MY_PERMISSIONS_REQUEST_LOCATION = 100



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
    companion object {
    var hikingLocationListener: LocationListener? = null
    }

    @SuppressLint("MissingPermission")
    fun startListeningUserLocation(context: Context, myListener: HikingLocationListener?) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val locationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                if (myListener != null) {
                    myListener.onLocationChanged(location)
                } // calling listener to inform that updated location is available
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

        hikingLocationListener = locationListener
    }

    fun stopListeningUserLocation(context: Context) {
        val mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hikingLocationListener?.let { mLocationManager.removeUpdates(it) }
        hikingLocationListener = null
    }
}