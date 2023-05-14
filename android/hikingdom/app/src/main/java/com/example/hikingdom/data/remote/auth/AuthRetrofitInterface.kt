package com.example.hikingdom.data.remote.auth

import com.example.hikingdom.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @GET("info/today/mountain")
    fun getUserInfo(): Call<UserResponse>

    @POST("members/auth/refresh-token")
    fun refreshTokens(@Body refreshToken: TokenRequest): Call<TokenResponse>

//    @POST("/users")
//    fun signUp(@Body user: User): Call<AuthResponse>
//
//    @POST("/users/login")
//    fun login(@Body user: User): Call<AuthResponse>
//
//    @GET("/users/auto-login")
//    fun autoLogin(): Call<AuthResponse>
}