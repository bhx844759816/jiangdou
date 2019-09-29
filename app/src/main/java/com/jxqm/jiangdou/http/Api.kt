package com.jxqm.jiangdou.http

/**
 * Created by Administrator on 2019/9/8.
 */
object Api {


    const val HTTP_BASE_URL = "http://jxdou.natapp1.cc"

    //    const val HTTP_BASE_URL = "http://192.168.0.100:8080"
    const val SEND_SMS_CODE = "/send-validate-code"
    //登录并获取token
    const val GET_TOKEN = "/oauth/token"

    const val UPLOAD_IMG = "/upload/image"
    //注册
    const val REGISTER = "/register"
    //用户信息
    const val USER_INFO = "/me"
    //获取简历 和
    const val GET_USER_RESUME = "/resume"
    //提交简历
    const val UPLOAD_USER_RESUME = "/resume"
    //更新简历
    const val UPDATE_USER_RESUME = "/resume/update"
    //报名
    const val SING_UP_JOB = "/resume/sending"
    //接受offer
    const val ACCEPT_OFFER = "/resume/offer/accept"
    //拒绝Offer
    const val REFUSE_OFFER = "/resume/offer/refuse"
    //获取学历列表
    const val GET_EDU_LIST = "/code/academic"

    //企业类型
    const val COMPANY_TYPE = "/code/qylx"
    //人员规模
    const val COMPANY_PEOPLE = "/code/rygm"
    //所属行业
    const val COMPANY_JOB_TYPE = "/code/hyfl"
    //提交认证
    const val ATTESTATION_SUBMIT = "/employer/register"
    //获取认证状态
    const val GET_ATTESTATION_STATUS = "/employer/status"
    //获取认证信息
    const val GET_ATTESTATION_DETAILS = "/employer"
    //发布兼职 - 兼职类型 - 2级列表
    const val PUBLISH_JOB_TYPE = "/code/job_types_l2"
    //职位类型
    const val JOB_TYPES = "/code/job_types"
    //发布工作
    const val PUBLISH_JOB = "/employer/job"
    //等待发布
    const val PUBLISH_JOB_WAITE = "/employer/job/created"
    //等待审核
    const val PUBLISH_JOB_WAIT_EXAMIN = "/employer/job/audited"
    //正在招聘
    const val PUBLISH_JOB_UNDERWAY = "/employer/job/recruiting"
    //截至报名
    const val PUBLISH_END_SIGN_UP_JOB = "/employer/job/closed"
    //订单详情
    const val PUBLISH_JOB_ORDER_DETAILS = "/employer/order"
    //支付订单
//    const val PUBLISH_PAY_JOB_ORDER = "/employer/order/pay"
    //支付订单
    const val PUBLISH_PAY_JOB_ORDER = "/employer/order/frozen-deposit"
    //获取账户余额
    const val GET_ACCOUNT_BALANCE = "/account/balance"
    //首页轮播图
    const val HOME_SWIPER = "/home/swiper"
    //首页职位推荐
    const val HOME_JOB_RECOMMEND = "/home/job/recommend"
    //职位分类导航
    const val HOME_JOB_CAT = "/home/job/cat"
    //雇佣记录 - 已报名
    const val GET_SIGN_UP_EMPLOYEE_LIST = "/employer/job/sign"
    //雇佣记录 - 接受雇佣
    const val ACCEPT_EMPLOYEE = "/employer/job/offer"
    //雇佣记录 - 录用 - 已接受
    const val GET_ACCEPT_EMPLOYEE_LIST = "/employer/job/accept"
    //雇佣记录 - 录用 - 已拒绝 refuse
    const val GET_REFUSE_EMPLOYEE_LIST = "/employer/job/refuse"
    //雇佣记录 - 到岗
    const val GET_REPORTDUTY_LIST = "/employer/job/arrivaled"
    //雇佣记录 - 待结算
    const val GET_WAIT_SETTLEMENT_LIST = "/employer/job/unsettled"
    //雇员 - 已报名列表
    const val GET_EMPLOYEE_SIGN_LIST = "/employee/job/sign"
    //雇员- 已报名 - 截止报名列表
    const val GET_EMPLOYEE_CLOSED_SIGN_LIST = "/employee/job/closed"

    //雇员 - 已经结算列表
    const val GET_EMPLOYEE_SETTLE_LIST = "/employee/job/settle"
    //雇员 - 已经接受Offer列表
    const val GET_EMPLOYEE_OFFER_LIST = "/employee/job/offer"
    //雇员 - 已经到岗列表
    const val GET_EMPLOYEE_ARRIVAL_LIST = "/employee/job/arrival"
}