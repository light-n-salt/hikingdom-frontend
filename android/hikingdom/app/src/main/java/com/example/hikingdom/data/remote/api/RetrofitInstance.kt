package com.example.hikingdom.data.remote.api

import android.util.Log
import com.example.hikingdom.ApplicationClass
import com.example.hikingdom.data.remote.auth.AuthRetrofitInterface
import com.example.hikingdom.utils.getAccessToken
import com.example.hikingdom.utils.getRefreshToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    // retrofit 인스턴스를 만들어서 반환하는 함수
    companion object {
        fun getInstance(): Retrofit {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            return Retrofit
                .Builder()
                .baseUrl(ApplicationClass.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(httpLoggingInterceptor) // 로깅 인터셉터 추가
                        .build()
                )
                .build()
        }
    }

    // Authorization header에 accessToken을 추가하는 Interceptor
    private class DebugInterceptor: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            Log.d("originalRequest", "$originalRequest")
            return chain.proceed(originalRequest)
        }
    }

}