package com.example.hikingdom.data.remote

import com.google.gson.annotations.SerializedName

data class BaseRes(
    @SerializedName("message") val message: String,
    @SerializedName("timestamp") val timestamp: String?,
    @SerializedName("code") val code: String?,
    @SerializedName("result") val result: String?
)
