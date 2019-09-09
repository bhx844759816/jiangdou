package com.jxqm.jiangdou.config

import android.os.Environment
import java.io.File
import java.util.HashMap

class Constants {
    companion object {
        const val BASE_URL = "http://www.dadpat.com/"
        const val BASE_URL2 = "https://www.goofypapa.com"
        val APP_SAVE_DIR = (Environment.getDataDirectory().path + File.separator + "benbaba"
                + File.separator + "apk" + File.separator)


        const val DEVICE_WIFI_SSID = "dadpat"
        //    public static final String DEVICE_WIFI_SSID = "benbb";
        const val DEVICE_WIFI_PASSWORD = "dadpat@123"
        const val APP_DOWNLAND_NAME = "dadpat.apk"


        /**
         *     ************************LiveBus事件对象***************************************
         */
        //对话框的
        const val EVENT_KEY_LOADING_DIALOG = "event_key_loading_dialog"
        const val TAG_LOADING_DIALOG = "tag_loading_dialog"
        //http请求报错
        const val EVENT_KEY_HTTP_REQUEST_ERROR = "event_key_http_request_error"
        const val TAG_HTTP_REQUEST_ERROR = "tag_http_request_error"
        //
        const val EVENT_KEY_LOGIN = "event_key_login" //登录界面
        const val TAG_LOGIN_CODE_SUCCESS = "tag_login_code_success" //

        const val EVENT_KEY_PHONE_LOGIN = "event_key_phone_login" //手机号登录几面


        const val EVENT_KEY_VERIFY_CODE = "event_key_verify_code" //验证码界面
        const val TAG_SEND_SMS_CODE_RESULT = "tag_send_sms_code_result" //发送验证码结果
        const val TAG_GET_TOKEN_RESULT = "tag_get_token_result"
        const val TAG_GET_TOKEN_RESULT_ERROR = "tag_get_token_result_error"

        const val EVENT_KEY_FORGET_PSD = "event_key_forget_psd" //忘记密码界面

        const val EVENT_KEY_REGISTER = "event_key_register" //注册界面
        const val TAG_REGISTER_GET_CODE_SUCCESS = "tag_register_get_code_success"//获取验证码成功
        const val TAG_REGISTER_SUCCESS = "tag_register_success"//注册成功

        const val EVENT_KEY_SELECT_JOB_TYPE = "event_key_select_job_type"//选择兼职类型

        const val EVENT_KEY_PEOPLE_ATTESTATION = "event_key_people_attestation" // 企业认证

        const val EVENT_KEY_JOB_MANAGER = "event_key_job_manager" // 兼职管理界面


        const val EVENT_KEY_JOB_SEARCH = "event_key_job_search" // 工作搜索界面


        const val EVENT_KEY_JOB_LIST = "event_key_job_list" // 搜职位的Fragment

        const val EVENT_KEY_COMPANY_LIST = "event_key_company_list"//搜公司得Fragment


        const val EVENT_KEY_COMPANY_DETAILS = "event_key_company_details"//公司详情界面


        const val EVENT_KEY_MY_RESUME = "event_key_my_resume"//公司详情界面

        const val EVENT_KEY_MAIN_HOME = "event_key_main_home"//首页界面

        const val EVENT_KEY_MAIN_WORK = "event_key_main_work"//工作台界面

        const val EVENT_KEY_MAIN_MY = "event_key_main_my"//我的界面


    }
}