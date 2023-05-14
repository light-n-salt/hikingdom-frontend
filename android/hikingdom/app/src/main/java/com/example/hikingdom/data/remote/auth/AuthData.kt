package com.example.hikingdom.data.remote.auth

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("refreshToken")  val refreshToken: String,
)

data class TokenResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Token,
)

data class Token (
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)

data class UserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: HashMap<String, Any>,
)
