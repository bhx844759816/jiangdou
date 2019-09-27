package com.jxqm.jiangdou.http.interceptor

import android.text.TextUtils
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.http.Api
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加token请求头
 */
class TokenInterceptor : Interceptor {
    private val TOKEN_KEY = "Authorization"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = MyApplication.instance().accessToken
        LogUtils.i("token:$token")
        val path = originalRequest.url().encodedPath()
        val isNotAddToken = (path == Api.SEND_SMS_CODE ||
                path == Api.GET_TOKEN || path == Api.HOME_SWIPER ||
                path == Api.HOME_JOB_RECOMMEND || path == Api.HOME_JOB_CAT
                || path == Api.JOB_TYPES)
        if (!TextUtils.isEmpty(token) && !isNotAddToken) {
            val request = originalRequest
                .newBuilder()
                .addHeader(TOKEN_KEY, "Bearer $token")
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(originalRequest)
    }
}