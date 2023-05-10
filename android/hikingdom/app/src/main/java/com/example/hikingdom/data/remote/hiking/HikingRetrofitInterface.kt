package com.example.hikingdom.data.remote.hiking

import com.example.hikingdom.data.remote.BaseRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface HikingRetrofitInterface {
    @POST("hiking")
    fun saveHikingRecourd(@Body saveHikingRecordReq: SaveHikingRecordReq): Call<BaseRes>
}