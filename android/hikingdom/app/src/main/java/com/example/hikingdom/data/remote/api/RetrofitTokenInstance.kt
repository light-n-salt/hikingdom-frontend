package com.example.hikingdom.data.remote.api

import android.util.Log
import com.example.hikingdom.ApplicationClass.Companion.API_BASE_URL
import com.example.hikingdom.data.remote.auth.AuthRetrofitInterface
import com.example.hikingdom.data.remote.auth.TokenRequest
import com.example.hikingdom.utils.deleteJWT
import com.example.hikingdom.utils.getAccessToken
import com.example.hikingdom.utils.getRefreshToken
import com.example.hikingdom.utils.saveJWT
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitTokenInstance {

    // retrofit 인스턴스를 만들어서 반환하는 함수
    companion object {
        fun getInstance(): Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val gson = GsonBuilder().setLenient().create()

            return Retrofit
                .Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor) // 디버깅을 위한 로깅 인터셉터
                        .addInterceptor(
                            AuthorizationInterceptor()  // 헤더에 토큰 추가 인터셉터
                        )
                        .addInterceptor(refreshTokenInterceptor()) // 인증 만료 시 토큰 재발급 인터셉터
                        .build()
                )
                .build()
        }
    }

    // Authorization header에 accessToken을 추가하는 Interceptor
    private class AuthorizationInterceptor: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            var accessToken = getAccessToken()

            // accessToken이 없을 경우, 기존 요청 그대로 반환
            if (accessToken == null) {
                return chain.proceed(originalRequest)
            }

            // accessToken이 있을 경우, authorization header에 accessToken 추가
            val request = originalRequest.newBuilder()
                    .header("authorization", accessToken)
                    .build()

            return chain.proceed(request)
        }
    }

//     refreshToken이 만료되었을 경우, accessToken을 재발급하는 Interceptor
    private class refreshTokenInterceptor(): Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request() // 기존 요청 기억 (토큰 재발급 후 재요청을 위해서)
            val response = chain.proceed(originalRequest)

            // 인증이 안되었을 경우 토큰 재발급 로직
            if (response.code == 401 && response.message == "Unauthorized") {
                // 비동기 로직의 동기적 실행
                synchronized(this) {
                    val api = RetrofitInstance.getInstance().create(AuthRetrofitInterface::class.java) // 기존 인터셉터가 없는 순수한 retrofitInstance로 요청

                    var refreshToken = getRefreshToken()
                    val tokenRequest= TokenRequest(refreshToken!!)
                    var tokenResponse = api.refreshTokens(tokenRequest).execute() // 동기적으로 토큰 재발급 요청 결과 반환

                    if (tokenResponse.isSuccessful) {   // 재발급 요청이 성공적이었을 경우
                        val tokenBody = tokenResponse.body()
                        val newAccessToken = tokenBody?.result?.accessToken
                        val newRefreshToken = tokenBody?.result?.refreshToken

                        saveJWT(newAccessToken!!, newRefreshToken!!)    // 토큰 저장

                        response.close() // 기존 요청에 대한 응답은 닫은 후,
                        val newRequest = originalRequest.newBuilder()   // 기존 요청 새로운 토큰으로 재시도
                            .header("Authorization", newAccessToken )
                            .build()

                        return chain.proceed(newRequest)    // 재시도한 결과 반환
                    } else { // 재발급 요청이 실패한 경우
                        deleteJWT() // 토큰 삭제
                        // 로그인 액티비티로 가는 로직 추가
                    }
                }
            }

            // 인증관련 문제가 아닐 경우, response 그냥 반환
            return response
        }
    }
}
