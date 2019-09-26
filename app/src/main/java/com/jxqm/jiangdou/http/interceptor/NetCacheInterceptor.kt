package com.jxqm.jiangdou.http.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created By bhx On 2019/9/26 0026 09:06
 */
class NetCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val onlineCacheTime = 30//在线的时候的缓存过期时间，如果想要不缓存，直接时间设置为0
        return response.newBuilder()
            .header("Cache-Control", "public, max-age=$onlineCacheTime")
            .removeHeader("Pragma")
            .build()

    }
}