package com.example.hikingdom.utils;

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices

object LocationUtils {
    fun getCurrentLocation(context: Context): DoubleArray? {
        // 현재 위치 가져오기
        val locationManager: LocationManager = ContextCompat.getSystemService(
            context,
            LocationManager::class.java
        )!!
        // 위치 권한 체크
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "위치 권한이 없습니다.", Toast.LENGTH_SHORT).show()
            return null
        }
        val loc_Current: Location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!!
        val cur_lat = loc_Current.getLatitude();
        val cur_lng = loc_Current.getLongitude();

        return doubleArrayOf(cur_lat, cur_lng)
    }
}
