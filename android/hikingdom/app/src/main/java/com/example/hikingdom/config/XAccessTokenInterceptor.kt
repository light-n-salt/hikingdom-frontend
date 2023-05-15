package com.example.hikingdom.config

import com.example.hikingdom.ApplicationClass.Companion.ACCESS_TOKEN_KEY
import com.example.hikingdom.utils.getAccessToken
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class XAccessTokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getAccessToken()

        jwtToken?.let{
            builder.addHeader(ACCESS_TOKEN_KEY, jwtToken)
        }

        return chain.proceed(builder.build())
    }
}