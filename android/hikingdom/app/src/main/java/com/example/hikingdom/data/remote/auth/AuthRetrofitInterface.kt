package com.example.hikingdom.data.remote.auth

import com.example.hikingdom.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @GET("members")
    fun getUserInfo(): Call<UserResponse>

    @POST("members/auth/refresh-token")
    fun refreshTokens(@Body refreshToken: TokenRequest): Call<TokenResponse>

}