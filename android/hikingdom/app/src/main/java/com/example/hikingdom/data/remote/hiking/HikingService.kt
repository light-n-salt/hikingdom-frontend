package com.example.hikingdom.data.remote.hiking

import android.util.Log
import com.example.hikingdom.ApplicationClass.Companion.TAG
import com.example.hikingdom.data.remote.BaseRes
import com.example.hikingdom.data.remote.api.RetrofitTokenInstance
import com.example.hikingdom.ui.main.hiking.SaveHikingRecordView
import com.example.hikingdom.utils.NetworkModule
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object HikingService {
    val retrofit = RetrofitTokenInstance.getInstance()

    fun saveHikingRecord(saveHikingRecordView: SaveHikingRecordView, saveHikingRecordReq: SaveHikingRecordReq) {
        val saveHikingRecordService = retrofit.create(HikingRetrofitInterface::class.java)

        saveHikingRecordService.saveHikingRecourd(saveHikingRecordReq).enqueue(object :
            Callback<BaseRes> {
            override fun onResponse(call: Call<BaseRes>, response: Response<BaseRes>) {

                if(response.isSuccessful){
                    val resp = response.body()!!
                    Log.d("resp", resp.toString())
                    saveHikingRecordView.onSaveHikingRecordSuccess(resp.message)
                }else{
                    saveHikingRecordView.onSaveHikingRecordFailure(response.message())
                }
            }

            override fun onFailure(call: Call<BaseRes>, t: Throwable) {
                Log.d("${TAG}/API-ERROR", t.message.toString())
                saveHikingRecordView.onSaveHikingRecordFailure("네트워크 오류가 발생했습니다.")
            }
        })
    }
}