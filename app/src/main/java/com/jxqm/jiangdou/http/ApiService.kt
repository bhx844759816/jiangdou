package com.jxqm.jiangdou.http

import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Administrator on 2019/9/8.
 */
interface ApiService {
    @FormUrlEncoded
    @POST(Api.SEND_SMS_CODE)
    fun sendSmsCode(@FieldMap map: Map<String, String>): Observable<HttpResult<Any>>


}