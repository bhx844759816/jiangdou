package com.jxqm.jiangdou.http

import com.jxqm.jiangdou.model.*
import com.jxqm.jiangdou.ui.attestation.model.CompanyTypeModel
import com.jxqm.jiangdou.model.AttestationStatusModel
import com.jxqm.jiangdou.model.SwpierModel
import com.jxqm.jiangdou.ui.order.model.OrderDetailsModel
import com.jxqm.jiangdou.ui.user.model.EduModel
import com.jxqm.jiangdou.ui.user.model.ResumeModel
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @Multipart
    @POST(Api.REFRESH_TOKEN)
    fun refreshToken(@PartMap params: MutableMap<String, RequestBody>): Call<TokenModel>

    @Headers("Content-type:application/json")
    @POST(Api.PUBLISH_JOB)
    fun uploadPublishJob(@Body body: RequestBody): Observable<HttpResult<String>>

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
    fun getOrderDetails(@Path("jobId") jobId: String): Observable<HttpResult<OrderDetailsModel>>

    /**
     * 支付订单
     */
    @Multipart
    @POST(Api.PUBLISH_PAY_JOB_ORDER)
    fun payOrder(@Part("jobId") jobId: Long): Observable<HttpResult<Any>>

    /**
     * 获取账户余额
     */
    @GET(Api.GET_ACCOUNT_BALANCE)
    fun getAccountBalance(): Observable<HttpResult<String>>

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
     * 更新用户简历
     */
    @POST(Api.UPDATE_USER_RESUME)
    fun updateUserResume(@Body body: MultipartBody): Observable<HttpResult<Any>>

    /**
     * 获取用户简历
     */
    @GET(Api.GET_USER_RESUME)
    fun getUserResume(): Observable<HttpResult<ResumeModel>>

    /**
     * 获取用户简历通过用户ID
     */
    @GET(Api.GET_USER_RESUME + "/{userId}")
    fun getUserResume(@Path("userId") userId: Long): Observable<HttpResult<ResumeModel>>

    /**
     * 报名工作
     */
    @Multipart
    @POST(Api.SING_UP_JOB)
    fun singUpJob(@Part("jobId") jobId: Long): Observable<HttpResult<Any>>

    /**
     * 获取雇主的详情
     */
    @GET(Api.GET_EMPLOYER_DETAILS + "/{employerId}")
    fun getEmployerDetails(@Path("employerId") employerId: String): Observable<HttpResult<AttestationStatusModel>>

    /**
     * 接受offer
     */
    @Multipart
    @POST(Api.ACCEPT_OFFER)
    fun acceptOffer(@Part("id") jobId: Int): Observable<HttpResult<Any>>

    /**
     * 拒绝Offer
     */
    @Multipart
    @POST(Api.REFUSE_OFFER)
    fun refuseOffer(@Part("id") jobId: Int): Observable<HttpResult<Any>>

    /**
     * 获取首页轮播图
     */
    @GET(Api.HOME_SWIPER)
    fun getHomeSwiper(): Observable<HttpResult<List<SwpierModel>>>

    /**
     * 获取首页推荐列表
     */
    @GET(Api.HOME_JOB_RECOMMEND)
    fun getHomeRecommend(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 获取职位分类导航
     * @param jobTypeId 职位分类ID
     */
    @GET(Api.HOME_JOB_CAT + "/{jobTypeId}")
    fun getHomeJobCat(@Path("jobTypeId") jobTypeId: String): Observable<HttpResult<Any>>

    /**
     * 获取首页兼职分类
     */
    @GET(Api.JOB_TYPES)
    fun getHomeJobType(): Observable<HttpResult<List<JobTypeModel>>>

    @GET(Api.JOB_HOT_TYPES)
    fun getHomeHotJobType(): Observable<HttpResult<List<JobTypeModel>>>

    /**
     * 获取雇佣记录 - 已报名
     */
    @GET(Api.GET_SIGN_UP_EMPLOYEE_LIST)
    fun getSignUpEmployeeList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 批量录用
     */
    @Headers("Content-type:application/json")
    @POST(Api.ACCEPT_EMPLOYEE)
    fun acceptResume(@Body body: RequestBody): Observable<HttpResult<Any>>

    /**
     * 批量驳回录用
     */
    @Headers("Content-type:application/json")
    @POST(Api.REGECTED_EMPLOYEE)
    fun regectedResume(@Body body: RequestBody): Observable<HttpResult<Any>>

    /**
     * 雇佣记录 - 已邀请
     */
    @GET(Api.GET_INVITE_EMPLOYEE_LIST)
    fun getInviteEmployeeList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已邀请
     */
    @GET(Api.GET_ACCEPT_EMPLOYEE_LIST)
    fun getAcceptEmployeeList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已拒绝
     */
    @GET(Api.GET_REFUSE_EMPLOYEE_LIST)
    fun getRefuseEmployeeList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已拒绝
     */
    @GET(Api.GET_NO_REPLY_EMPLOYEE_LIST)
    fun getNoReplyEmployeeList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已到岗
     */
    @GET(Api.GET_REPORTDUTY_LIST)
    fun getReportDutyList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 待结算列表
     */
    @GET(Api.GET_WAIT_SETTLEMENT_LIST)
    fun getWaitSettlementList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已结算 - 已出账
     */
    @GET(Api.GET_SETTLED_FINISH_LIST)
    fun getSettleFinishList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已结算 - 待确认
     */
    @GET(Api.GET_SETTLED_WAIT_CONFIRM_LIST)
    fun getSettleWaitConfirmList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇佣记录 - 已结算 - 已拒绝
     */
    @GET(Api.GET_SETTLED_REFUSE_LIST)
    fun getSettleRefuseList(
        @Query("jobId") jobId: Long,
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int
    ): Observable<HttpResult<EmployeeResumeModelWrap>>

    /**
     * 雇员 - 已报名
     */
    @GET(Api.GET_EMPLOYEE_SIGN_LIST)
    fun getEmployeeSignList(): Observable<HttpResult<List<JobEmployeeModel>>>

    /**
     *雇员 - 截止报名
     */
    @GET(Api.GET_EMPLOYEE_CLOSED_SIGN_LIST)
    fun getEmployeeClosedSignList(): Observable<HttpResult<List<JobEmployeeExceptionModel>>>

    /**
     * 雇员 - 已录用 - 列表
     */
    @GET(Api.GET_EMPLOYEE_OFFER_LIST)
    fun getEmployeeOfferList(): Observable<HttpResult<List<JobEmployeeModel>>>

    /**
     * 雇员 - 已录用 -失效
     */
    @GET(Api.GET_EMPLOYEE_OFFER_INVALID_LIST)
    fun getEmployeeInvalidList(): Observable<HttpResult<List<JobEmployeeExceptionModel>>>

    /**
     * 雇员 - 已到岗
     */
    @GET(Api.GET_EMPLOYEE_ARRIVAL_LIST)
    fun getEmployeeArrivalList(): Observable<HttpResult<List<JobEmployeeModel>>>

    /**
     * 雇员 - 已结算
     */
    @GET(Api.GET_EMPLOYEE_SETTLE_LIST)
    fun getEmployeeSettleList(): Observable<HttpResult<List<JobEmployeeModel>>>

    /**
     * 获取兼职的公司列表
     */
    @GET(Api.GET_SEARCH_COMPANY_LIST)
    fun getSearchCompanyList(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int, @Query("searchKey") searchKey: String
    ): Observable<HttpResult<List<JobDetailsModel>>>

    /**
     * 获取兼职列表
     */
    @GET(Api.GET_SEARCH_JOB_LIST)
    fun getSearchJobList(
        @Query("pageNo") pageNo: Int,
        @Query("pageSize") pageSize: Int, @Query("searchKey") searchKey: String
    ): Observable<HttpResult<JobDetailsWrapModel>>

    /**
     * 获取全部兼职列表
     */
    @GET(Api.GET_SEARCH_JOB_LIST)
    fun getAllSearchJobList(@QueryMap paramsMap: Map<String, String>): Observable<HttpResult<JobDetailsWrapModel>>

    @GET(Api.GET_SEARCH_JOB_LIST + "/{jobId}")
    fun getJobDetails(@Path("jobId") jobId: String): Observable<HttpResult<JobDetailsModel>>

    /**
     * 获取雇主发布的职位列表
     */
    @GET(Api.GET_EMPLOYER_JOB_LIST)
    fun getEmployerJobList(@Query("userId") employerId: String): Observable<HttpResult<List<JobDetailsModel>>>

    /**
     * 删除职位通过职位ID
     */
    @DELETE(Api.DELETE_JOB_BY_ID + "/{jobId}")
    fun deleteJobById(@Path("jobId") jobTypeId: String): Observable<HttpResult<Any>>

    /**
     * 获取签到二维码
     */
    @GET(Api.GET_SIGN_UP_QR_CODE + "/{jobId}")
    fun getSignUpQrCode(@Path("jobId") jobId: String): Observable<HttpResult<String>>

    /**
     * 签到
     */
    @Multipart
    @POST(Api.EMPLOYEE_ARRIVALED)
    fun employeeArrival(@Part("id") id: Long): Observable<HttpResult<Any>>

    /**
     * 单结
     */
    @Multipart
    @POST(Api.SINGLE_SETTLE_WORK)
    fun singleSettleWork(@Part("id") id: Long, @Part("amount") amount: Int): Observable<HttpResult<Any>>

    /**
     * 合并结算
     */
    @Headers("Content-type:application/json")
    @POST(Api.MERGE_SETTLE_WORK)
    fun mergeSettleWork(@Body body: RequestBody): Observable<HttpResult<Any>>

    /**
     * 雇员 - 拒绝结算
     */
    @Multipart
    @POST(Api.EMPLOYEE_REFUSE_SETTLE)
    fun refuseSettle(@Part("jobWorkId") jobWorkId: Long): Observable<HttpResult<Any>>

    /**
     * 雇员 - 已结算
     */
    @Multipart
    @POST(Api.EMPLOYEE_ACCEPT_SETTLE)
    fun acceptSettle(@Part("jobWorkId") jobWorkId: Long): Observable<HttpResult<Any>>
}