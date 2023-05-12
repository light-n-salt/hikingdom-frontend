package com.example.hikingdom.utils

import com.example.hikingdom.ApplicationClass.Companion.BASE_URL
import com.example.hikingdom.ApplicationClass.Companion.RETROFIT_BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


object NetworkModule {

    fun getRetrofit(): Retrofit {

        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(30000, TimeUnit.MILLISECONDS)
            .connectTimeout(30000, TimeUnit.MILLISECONDS)
            .writeTimeout(30000, TimeUnit.MILLISECONDS)
//            .addInterceptor(RequestInterceptor())
            .build()

        val gson = GsonBuilder().setLenient().create()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//            .registerTypeAdapter(
//                LocalDateTime::class.java,
//                JsonDeserializer { json, _, _ ->
//                    LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_DATE_TIME)
//                }).create()


        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL) // 기본 URL 세팅
            .client(client) //Logger 세팅
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit
    }
}

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
//        val token = "Bearer "+getJwt()!!
        val token = "Bearer "+ "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sInR5cGUiOiJhY2Nlc3MiLCJpYXQiOjE2ODM3MDEzNjYsImV4cCI6MTY4MzcwNDk2Nn0.HRFGnYd3VT4Rj-c4lW3nJsrP9AEwgWUxod0GCNufLKI"

        builder.addHeader("Authorization", token)

        return chain.proceed(builder.build())
    }
}

//class HeaderInterceptor constructor(private val token: String) : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val token = "Bearer $token"
//        val newRequest = chain.request().newBuilder()
//            .addHeader("Authorization", token)
//            .build()
//        return chain.proceed(newRequest)
//    }
//}

//internal class AuthInterceptor : Interceptor, LogoutListView, AccessTokenView {
//
//    val viewModel = ViewModel.getInstance()
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//
//        var request = chain.request()
//        var builder = request.newBuilder()
//
//        setAuthHeader(builder, getAccessToken()) //write current token to request
//
//        request = builder.build(); //overwrite old request
//        var response = chain.proceed(request)
//        Log.d("response", response.toString())
//        val rawJson = response.body?.string() ?: "{}"
//        val type = object : TypeToken<ResponseWrapper<*>>() {}.type
//        val gson = Gson()
//        val res = gson.fromJson<ResponseWrapper<*>>(rawJson, type)
//        val responseJson = gson.toJson(res)
//
//        if (res.code != null) {
//            when (res.code) {
//                2007 -> {
//                    Log.d("AuthInterceptor", "재발급")
//                    getRefreshToken()
//                    Thread.sleep(10000)
//                    Log.d("재발급 성공", request.toString())
//                    setAuthHeader(builder, getAccessToken()) //set auth token to updated
//                    request = builder.build()
//                    Log.d("request 다시 요청", request.toString())
//                    return chain.proceed(request) //repeat request with new token
//
//                }
//            }
//        }
//        return return response.newBuilder()
//            .body(responseJson.toResponseBody())
//            .build()
//
//    }
//
//    private fun setAuthHeader(builder: Request.Builder, token: String?) {
//        if (token != null) //Add Auth token to each request if authorized
//            builder.header(ApplicationClass.X_ACCESS_TOKEN, token)
//    }
//
//    private fun getRefreshToken() {
//        AccessTokenService.getAccessToken(this)
//    }
//
//    fun Response.extractResponseJson(): JSONObject {
//        val jsonString = this.body?.string() ?: "{}"
//        return JSONObject(jsonString)
//    }
//
//    override fun onLogOutSuccess() {
//        Log.d("로그아웃", "성공")
//        val intent = Intent(ApplicationClass.getInstance().context(), SplashActivity::class.java)
//        intent.flags =
//            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        ApplicationClass.getInstance().context().startActivity(intent)
//    }
//
//    override fun onLogOutFailure(code: Int, message: String) {
////        Log.d("로그아웃","실패")
////        val intent = Intent(ApplicationClass.getInstance().context(), SplashActivity::class.java)
////        intent.flags =
////            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
////        ApplicationClass.getInstance().context().startActivity(intent)
//    }
//
//    override fun onAccessTokenCode(boolean: Boolean) {
//        Log.d("onAccessTokenCode", boolean.toString())
//        viewModel.jwtRefreshSuccess.value = boolean
//    }
//}
