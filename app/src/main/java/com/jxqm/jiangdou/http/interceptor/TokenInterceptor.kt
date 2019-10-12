package com.jxqm.jiangdou.http.interceptor

import android.text.TextUtils
import com.bhx.common.http.RetrofitManager
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.ApiService
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call

/**
 * 添加token请求头
 */
class TokenInterceptor : Interceptor {
    private val TOKEN_KEY = "Authorization"
    private val needTokenPathArray = arrayOf("employer","resume","employee","account","search")
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MyApplication.instance().accessToken
        LogUtils.i("请求 token:$token")
        val request = chain.request().newBuilder()
        val path = chain.request().url().encodedPath()
        val pathFirst = path.split("/")[1]
        if (needTokenPathArray.contains(pathFirst)) {
            token?.let {
                request.addHeader(TOKEN_KEY, "Bearer $token")
            }
        }
        val proceed = chain.proceed(request.build())
        val code = proceed.code()
        LogUtils.i("请求 path:$pathFirst code:$code")
        if (code == 401 && !TextUtils.isEmpty(MyApplication.instance().refreshToken)) {
            val newToken = getNewToken(MyApplication.instance().refreshToken!!)
            val newRequest = chain.request().newBuilder()
                .addHeader(TOKEN_KEY, "Bearer $newToken")
                .build()
            LogUtils.i("刷新 token:$token")
            return chain.proceed(newRequest)
        }
        return proceed
    }

    private fun getNewToken(refreshToken: String): String {
        val apiService = RetrofitManager.getInstance().createApiService(ApiService::class.java)
        val params = mapOf(
            "refresh_token" to refreshToken, "grant_type" to "refresh_token",
            "client_id" to "jxdou_web", "client_secret" to "123456"
        )
        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        val call = apiService.refreshToken(requestBodyMaps)
        val body = call.execute().body()
        body?.let {
            //存储
            MyApplication.instance().saveToken(it)
            return it.access_token
        }
        return ""
    }
}