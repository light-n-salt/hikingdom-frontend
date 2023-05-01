package com.example.hikingdom.ui.main.hiking

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.collections.ArrayList
import kotlin.math.round
import kotlin.math.roundToInt

class HikingViewModel : ViewModel() {
    var duration = MutableLiveData<String>()
    var totalDistance = MutableLiveData<String>()
    var totalAltitude = MutableLiveData<String>()
    var altitudeList = MutableLiveData<Double>()
    var latitude = MutableLiveData<Double>()
    var longitude = MutableLiveData<Double>()
    var altitude = MutableLiveData<Double>()


    // 위도, 경도, 고도 list
    var locations = MutableLiveData<ArrayList<Location>>()

    init {
        duration.value = "00:00:00"
        totalDistance.value = "00.00km"
        locations.value = ArrayList()
        latitude.value = 0.0
        longitude.value = 0.0
        altitude.value = 0.0
    }

    fun getLocations(){

    }

    fun setDuration(d: Int){
        duration.value = timeToStr(d)
    }

    fun setTotalDistance(td : Float){
        totalDistance.value = ((td / 1000f * 100f).roundToInt() / 100f).toString() + "km"
    }

    fun setCurrentLocation(l : Location){
        var rLat = round(l.latitude*10000) / 10000
        var rLong = round(l.longitude*10000) / 10000
        var rAlt = round(l.altitude*10000) / 10000
        latitude.value = rLat
        longitude.value = rLong
        altitude.value = rAlt
    }

//    fun setTotalAltitude(){
//        ArrayList<Double>
//        val maxAlt = Collections.max()
//        val minAlt = Collections.min(altitudeList.value)
//        totalAltitude.value = (maxAlt - minAlt).toString() + "m"
//    }

    // time -> hh:mm:ss
    fun timeToStr(t: Int): String {
        val m = t / 60
        var second = (t % 60).toString()

        val hour = (m / 60).toString()
        var minute = (m % 60).toString()

        if (minute.length == 1)
            minute = "0$minute"

        if (second.length == 1)
            second = "0$second"

        return if (hour != "0")
            "$hour:$minute:$second"
        else
            "$minute:$second"
    }


}