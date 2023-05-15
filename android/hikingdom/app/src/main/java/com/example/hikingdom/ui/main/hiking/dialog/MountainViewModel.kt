package com.example.hikingdom.ui.main.hiking.dialog

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hikingdom.data.remote.api.RetrofitTokenInstance
import com.example.hikingdom.data.remote.hiking.HikingRetrofitInterface
import com.example.hikingdom.data.remote.hiking.Mountain
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.launch

class MountainViewModel: ViewModel() {
    val retrofitInterface: HikingRetrofitInterface = RetrofitTokenInstance.getInstance().create(HikingRetrofitInterface::class.java)

    private val _result = MutableLiveData<List<Mountain>>()
    val result: LiveData<List<Mountain>>
        get() = _result

    fun getNearMountains(lat: Float, lng: Float) = viewModelScope.launch {
        Log.d("respose", retrofitInterface.getNearMountains(lat, lng).toString())
    }
}