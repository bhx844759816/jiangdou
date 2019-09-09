package com.jxqm.jiangdou.http

import com.jxqm.jiangdou.model.TokenModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Created by Administrator on 2019/9/8.
 */
interface ApiService {
    @Headers("Content-type:application/json")
    @POST(Api.SEND_SMS_CODE)
    fun sendSmsCode(@Body body: RequestBody): Observable<HttpResult<Any>>

    @Multipart
    @POST(Api.GET_TOKEN)
    fun getToken(@PartMap params: MutableMap<String, RequestBody>): Observable<TokenModel>

    @Headers("Content-type:application/json")
    @POST(Api.SEND_SMS_CODE)
    fun register(@Body body: RequestBody): Observable<HttpResult<Any>>

    /**
     * username
    password=pasword(密码）/code(验证码）
    grant_type=password
    client_id=jxdou_web
    client_secret=123456
    auth_type= password/sms
    device_id = //设备ID

     */
}