package com.jxqm.jiangdou.config

import android.os.Environment
import java.io.File
import java.util.HashMap

class Constants {
    companion object {
        const val BASE_URL = "http://www.dadpat.com/"
        const val BASE_URL2 = "https://www.goofypapa.com"
        val APP_SAVE_DIR =
            (Environment.getExternalStorageDirectory().absolutePath + File.separator + "jiangdou" + File.separator)

        val MAPVIEW_FILENAME = "map.png"

        const val DEVICE_WIFI_SSID = "dadpat"
        //    public static final String DEVICE_WIFI_SSID = "benbb";
        const val DEVICE_WIFI_PASSWORD = "dadpat@123"
        const val APP_DOWNLAND_NAME = "dadpat.apk"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"


        /**
         *     ************************LiveBus事件对象***************************************
         */
        //对话框的
        const val EVENT_KEY_LOADING_DIALOG = "event_key_loading_dialog"
        const val TAG_LOADING_DIALOG = "tag_loading_dialog"
        //http请求报错
        const val EVENT_KEY_HTTP_REQUEST_ERROR = "event_key_http_request_error"
        const val TAG_HTTP_REQUEST_ERROR = "tag_http_request_error"
        //个人中心
        const val EVENT_KEY_LOGIN = "event_key_login" //登录界面
        const val TAG_LOGIN_CODE_SUCCESS = "tag_login_code_success" //
        const val EVENT_KEY_MAIN_MY = "event_key_main_my"//我的界面
        const val TAG_MAIN_MY_LOGIN_SUCCESS = "tag_main_my_login_success"
        const val EVENT_KEY_PHONE_LOGIN = "event_key_phone_login" //手机号登录几面
        const val TAG_PHONE_LOGIN_SUCCESS = "tag_phone_login_success" //
        const val EVENT_KEY_VERIFY_CODE = "event_key_verify_code" //验证码界面
        const val TAG_SEND_SMS_CODE_RESULT = "tag_send_sms_code_result" //发送验证码结果
        const val TAG_GET_USER_INFO_SUCCESS = "tag_get_token_result"
        const val TAG_GET_TOKEN_RESULT_ERROR = "tag_get_token_result_error"
        const val EVENT_KEY_FORGET_PSD = "event_key_forget_psd" //忘记密码界面
        const val EVENT_KEY_REGISTER = "event_key_register" //注册界面
        const val TAG_REGISTER_GET_CODE_SUCCESS = "tag_register_get_code_success"//获取验证码成功
        const val TAG_REGISTER_SUCCESS = "tag_register_success"//注册成功
        //发布兼职模块
        const val EVENT_KEY_JOB_PUBLISH = "event_key_job_publish"//发布兼职
        const val TAG_PUBLISH_JOB_ATTESTATION_DETAILS = "tag_publish_job_attestation_details" //发布兼职中得兼职类型
        const val TAG_PUBLISH_JOB_TYPE = "tag_publish_job_type" //发布兼职中得兼职类型
        const val TAG_PUBLISH_JOB_MESSAGE = "tag_publish_job_message" //发布兼职中得兼职类型
        const val TAG_PUBLISH_JOB_TIME = "tag_publish_job_time"//发布兼职的工作时间选择
        const val TAG_PUBLISH_JOB_EMPLOYER_PREVIEW = "tag_publish_job_employer_preview"//预览简历
        const val TAG_PUBLISH_JOB_EMPLOYER_PUBLISH = "tag_publish_job_employer_publish"//立即发布
        const val TAG_PUBLISH_JOB_SUCCESS = "tag_publish_job_success"//发布成功
        const val EVENT_KEY_SELECT_JOB_TYPE = "event_key_select_job_type"//选择兼职类型
        const val TAG_SELECT_JOB_TYPE_HOT = "tag_select_job_type_hot"//获取热门类型
        const val TAG_SELECT_JOB_TYPE_MORE = "tag_select_job_type_more"//获取更多类型

        const val EVENT_KEY_EMPLOY_RECORD_SIGN_UP = "event_key_employ_record_sign_up"//雇佣记录 - 已报名

        const val EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT = "event_key_employ_record_employment"//雇佣记录 - 已录用

        const val EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY = "event_key_employ_record_report_duty"//雇佣记录 - 已到岗

        const val EVENT_KEY_EMPLOY_RECORD_WAIT_PAY = "event_key_employ_record_wait_pay" //雇佣记录 - 待结算

        const val EVENT_KEY_EMPLOY_RECORD_PAY = "event_key_employ_record_pay"//雇佣记录已结算

        const val EVENT_KEY_JOB_PUBLISH_DETAILS = "event_key_job_publish_details" //发布兼职支付押金的详情界面


        //认证
        const val EVENT_KEY_COMPANY_ATTESTATION = "event_key_company_attestation" // 企业认证
        const val TAG_GET_COMPANY_ITEM_RESULT = "tag_get_company_item_result"
        const val TAG_GET_COMPANY_ATTESTATION_STATUS = "tag_get_company_attestation_status"
        const val EVENT_KEY_PEOPLE_ATTESTATION = "event_key_people_attestation" // 身份认证
        const val TAG_PEOPLE_ATTESTATION_SUBMIT_SUCCESS = "tag_people_attestation_submit_success" //上传成功

        const val EVENT_KEY_WAIT_PUBLISH_JOB = "event_key_wait_publish_job"//等待发布
        const val TAG_GET_WAIT_PUBLISH_JOB_LIST_SUCCESS = "tag_get_wait_publish_job_list_success"//
        const val TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR = "tag_get_wait_publish_job_list_error"//

        const val EVENT_KEY_WAIT_EXAMINE_JOB = "event_key_wait_examine_job"//等待审核
        const val TGA_GET_WAIT_EXAMINE_JOB_LIST_SUCCESS = "tga_get_wait_examine_job_list_success"
        const val TAG_GET_WAIT_EXAMINE_JOB_LIST_ERROR = "tag_get_wait_examine_job_list_error"

        const val EVENT_KEY_PUBLISHING_JOB = "event_key_publishing_job"//正在招聘
        const val TAG_GET_PUBLISHING_JOB_LIST_SUCCESS = "tag_get_publishing_job_list_success"
        const val TAG_GET_PUBLISHING_JOB_LIST_ERROR = "tag_get_publishing_job_list_error"

        const val EVENT_KEY_END_SIGN_UP = "event_key_end_sign_up"//截止报名
        const val TAG_GET_END_SIGN_UP_JOB_LIST_SUCCESS = "tag_get_end_sign_up_job_list_success"
        const val TAG_GET_END_SIGN_UP_JOB_LIST_ERROR = "tag_get_end_sign_up_job_list_error"

        //订单
        const val EVENT_KEY_PAYMENT_ORDER = "event_key_end_sign_up"//支付订单
        const val TAG_GET_ORDER_DETAILS_SUCCESS = "tag_get_order_details_success"//获取订单详情成功
        const val TAG_GET_USER_ACCOUNT_BALANCE_SUCCESS = "tag_get_user_account_balance_success"//获取账户余额
        const val TAG_PAY_ORDER_SUCCESS = "tag_pay_order_success"//支付订单成功

        //雇员  EmployeeSignUp
        const val EVENT_KEY_EMPLOYEE_SIGN_UP = "event_key_employee_sign_up"//雇员-已报名

        const val EVENT_KEY_EMPLOYEE_EMPLOYMENT = "event_key_employee_employment"//雇员 - 已录用

        const val EVENT_KEY_EMPLOYEE_REPORT_DUTY = "event_key_employee_report_duty"//雇员 - 已到岗

        const val EVENT_KEY_EMPLOYEE_PAY_FINISH = "event_key_employee_pay_finish"//雇员 - 已结算

        const val EVENT_KEY_JOB_MANAGER = "event_key_job_manager" // 兼职管理界面

        const val EVENT_KEY_JOB_SEARCH = "event_key_job_search" // 工作搜索界面


        const val EVENT_KEY_JOB_LIST = "event_key_job_list" // 搜职位的Fragment

        const val EVENT_KEY_COMPANY_LIST = "event_key_company_list"//搜公司得Fragment


        const val EVENT_KEY_COMPANY_DETAILS = "event_key_company_details"//公司详情界面


        const val EVENT_KEY_MY_RESUME = "event_key_my_resume"//简历详情
        const val TAG_GET_EDU_LIST_RESULT = "tag_get_edu_list_result"//获取学历列表
        const val TAG_GET_USER_RESUME_RESULT = "tag_get_user_resume_result"//获取用户简历详情
        const val TAG_UPLOAD_RESUME_RESULT = "tag_get_user_resume_result"//上传简历结果

        const val EVENT_KEY_MAIN = "event_key_main"//主界面

        const val EVENT_KEY_MAIN_HOME = "event_key_main_home"//首页界面
        const val TAG_GET_HOME_SWIPER = "tag_get_home_swiper"//首页轮播图
        const val TAG_GET_HOME_JOB_TYPE = "tag_get_home_job_type"//首页兼职类型列表
        const val TAG_GET_HOME_RECOMMEND_LIST = "tag_get_home_recommend_list"//首页兼职推荐列表
        const val EVENT_KEY_MAIN_WORK = "event_key_main_work"//工作台界面
        const val TAG_USER_INFO_UPDATE = "tag_user_info_update"//用户信息


    }
}