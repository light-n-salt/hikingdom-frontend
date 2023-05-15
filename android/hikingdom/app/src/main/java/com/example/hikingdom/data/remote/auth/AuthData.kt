package com.example.hikingdom.data.remote.auth

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("refreshToken")  val refreshToken: String,
)

data class Token (
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String,
)

data class TokenResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Token,
)

data class User (
    @SerializedName("email") val email: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("profileUrl") val profileUrl: String,
    @SerializedName("level") val level: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("clubId") val clubId: Int? = null,
)

data class UserResponse(
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: User,
)