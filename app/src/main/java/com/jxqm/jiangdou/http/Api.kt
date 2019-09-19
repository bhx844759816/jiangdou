package com.jxqm.jiangdou.http

/**
 * Created by Administrator on 2019/9/8.
 */
object Api {


    const val HTTP_BASE_URL = "http://jxdou.natapp1.cc/"

    const val SEND_SMS_CODE = "/send-validate-code"
    //登录并获取token
    const val GET_TOKEN = "/oauth/token"
    //注册
    const val REGISTER = "/register"
    //用户信息
    const val USER_INFO = "/me"
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
    //发布兼职 - 兼职类型
    const val PUBLISH_JOB_TYPE = "/code/job_types_l2"
}