package com.jxqm.jiangdou.http

import com.jxqm.jiangdou.model.CompanyTypeModel
import com.jxqm.jiangdou.model.JobTypeModel
import com.jxqm.jiangdou.model.TokenModel
import com.jxqm.jiangdou.model.UserModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.http.PartMap
import com.google.gson.JsonObject
import retrofit2.http.POST
import retrofit2.http.Multipart


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
    @POST(Api.REGISTER)
    fun register(@Body body: RequestBody): Observable<HttpResult<Any>>

    @GET(Api.USER_INFO)
    fun getUserInfo(): Observable<HttpResult<UserModel>>

    @GET(Api.COMPANY_TYPE)
    fun getCompanyType(): Observable<HttpResult<List<CompanyTypeModel>>>

    @GET(Api.COMPANY_PEOPLE)
    fun getCompanyPeople(): Observable<HttpResult<List<CompanyTypeModel>>>

    @GET(Api.COMPANY_JOB_TYPE)
    fun getCompanyJobType(): Observable<HttpResult<List<CompanyTypeModel>>>

    @GET(Api.PUBLISH_JOB_TYPE)
    fun getJobType(): Observable<HttpResult<List<JobTypeModel>>>

    @POST(Api.ATTESTATION_SUBMIT)
    fun submitAttestation(@Body body: MultipartBody): Observable<HttpResult<Any>>

    @GET(Api.GET_ATTESTATION_DETAILS)
    fun getAttestationDetails(): Observable<HttpResult<Any>>

    @GET(Api.GET_ATTESTATION_STATUS)
    fun getAttestationStatus(): Observable<HttpResult<Any>>

}