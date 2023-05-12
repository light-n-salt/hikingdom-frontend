package com.example.hikingdom.ui.main.hiking

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.round
import kotlin.math.roundToInt

class HikingViewModel : ViewModel() {
    var duration = MutableLiveData<String>()
    var totalDistance = MutableLiveData<String>()
    var latitude = MutableLiveData<Double>()    // 경로 저장과 관련 없음
    var longitude = MutableLiveData<Double>()
    var altitude = MutableLiveData<Double>()
    var isHikingStarted = MutableLiveData<Boolean>()

    // 위도, 경도, 고도 list
    var locations = MutableLiveData<ArrayList<Location>>()

    init {
        duration.value = "00:00"
        totalDistance.value = "0.000km"
        locations.value = ArrayList()
        latitude.value = 0.0
        longitude.value = 0.0
        altitude.value = 0.0
        isHikingStarted.value = false
    }

    fun getLocations(){

    }

    fun setDuration(d: Int){
        duration.value = timeToStr(d)
    }

    fun setTotalDistance(td: Int){
        round(td.toDouble())
        totalDistance.value = (td / 1000f).toString() + "km"
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

    override fun onCleared() {
        super.onCleared()
        Log.d("HikingViewModel", "onCleared 호출됨")
    }
}