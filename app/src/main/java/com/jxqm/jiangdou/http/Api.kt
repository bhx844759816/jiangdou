package com.jxqm.jiangdou.http

/**
 * Created by Administrator on 2019/9/8.
 */
object Api {


    const val HTTP_BASE_URL = "http://jxdou.natapp1.cc"

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
}