package com.jxqm.jiangdou.http

import android.text.TextUtils
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.MyApplication
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 添加token请求头
 */
class TokenInterceptor : Interceptor {
    private val TOKEN_KEY = "token"
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = MyApplication.instance().getAccessToken()
        LogUtils.i("token:$token")
        if (!TextUtils.isEmpty(token)) {
            val request = originalRequest
                .newBuilder()
                .addHeader(TOKEN_KEY, token)
                .build()
            return chain.proceed(request)
        }
        return chain.proceed(originalRequest)
    }
}