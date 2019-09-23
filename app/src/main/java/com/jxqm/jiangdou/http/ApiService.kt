package com.jxqm.jiangdou.http

import com.jxqm.jiangdou.model.*
import com.jxqm.jiangdou.ui.attestation.model.CompanyTypeModel
import com.jxqm.jiangdou.ui.attestation.model.AttestationStatusModel
import com.jxqm.jiangdou.ui.user.model.EduModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.http.PartMap
import retrofit2.http.POST
import retrofit2.http.Multipart


/**
 * Created by Administrator on 2019/9/8.
 */
interface ApiService {
    @Multipart
    @POST(Api.UPLOAD_IMG)
    fun uploadFile(@Part img: MultipartBody.Part): Observable<HttpResult<String>>

    @Headers("Content-type:application/json")
    @POST(Api.SEND_SMS_CODE)
    fun sendSmsCode(@Body body: RequestBody): Observable<HttpResult<Any>>

    @Multipart
    @POST(Api.GET_TOKEN)
    fun getToken(@PartMap params: MutableMap<String, RequestBody>): Observable<TokenModel>

    @Headers("Content-type:application/json")
    @POST(Api.PUBLISH_JOB)
    fun uploadPublishJob(@Body body: RequestBody): Observable<HttpResult<Any>>

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
    fun getAttestationStatus(): Observable<HttpResult<AttestationStatusModel>>

    /**
     * 等待发布
     */
    @GET(Api.PUBLISH_JOB_WAITE)
    fun getWaitPublishJob(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 等待审核
     */
    @GET(Api.PUBLISH_JOB_WAIT_EXAMIN)
    fun getWaitExaminJob(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 正在招聘
     */
    @GET(Api.PUBLISH_JOB_UNDERWAY)
    fun getUnderWayPublishJob(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 截止报名
     */
    @GET(Api.PUBLISH_END_SIGN_UP_JOB)
    fun getEndSignUpPublishJob(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 获取订单详情
     */
    @GET(Api.PUBLISH_JOB_ORDER_DETAILS + "/{jobId}")
    fun getOrderDetails(@Path("jobId") jobId: String): Observable<HttpResult<Any>>

    /**
     * 支付订单
     */
    @POST(Api.PUBLISH_PAY_JOB_ORDER)
    fun payOrder(): Observable<HttpResult<Any>>

    /**
     * 获取账户余额
     */
    @GET(Api.GET_ACCOUNT_BALANCE)
    fun getAccountBalance(): Observable<HttpResult<Any>>

    /**
     * 获取学历列表
     */
    @GET(Api.GET_EDU_LIST)
    fun getEduList(): Observable<HttpResult<List<EduModel>>>

    /**
     * 上传用户简历
     */
    @POST(Api.UPLOAD_USER_RESUME)
    fun uploadUserResume(@Body body: MultipartBody): Observable<HttpResult<Any>>

    /**
     * 获取用户简历
     */
    @GET(Api.GET_USER_RESUME)
    fun getUserResume(): Observable<HttpResult<Any>>

    /**
     * 报名工作
     */
    @POST(Api.SING_UP_JOB)
    fun singUpJob(@Part("jobResumeId") jobId: String): Observable<HttpResult<Any>>

    /**
     * 接受offer
     */
    @Multipart
    @POST(Api.ACCEPT_OFFER)
    fun acceptOffer(@Part("jobResumeId") jobId: String): Observable<HttpResult<Any>>

    /**
     * 拒绝Offer
     */
    @Multipart
    @POST(Api.REFUSE_OFFER)
    fun refuseOffer(@Part("jobResumeId") jobId: String): Observable<HttpResult<Any>>

}