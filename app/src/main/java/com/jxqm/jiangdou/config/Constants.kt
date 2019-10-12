package com.jxqm.jiangdou.config

import android.os.Environment
import java.io.File
import java.util.HashMap

class Constants {
    companion object {
        val APP_SAVE_DIR =
            (Environment.getExternalStorageDirectory().absolutePath + File.separator + "jiangdou" + File.separator)
        val MAPVIEW_FILENAME = "map.png"
        const val DEVICE_WIFI_SSID = "dadpat"
        //    public static final String DEVICE_WIFI_SSID = "benbb";
        const val DEVICE_WIFI_PASSWORD = "dadpat@123"
        const val APP_DOWNLAND_NAME = "dadpat.apk"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val SEARCH_KEY = "search_key"


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
        const val EVENT_KET_LOADING = "event_ket_loading"//loading界面
        const val TAG_LOADING_FINISH = "tag_loading_finish"//登录界面获取信息完成
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

        const val EVENT_KEY_EMPLOY_RECORD = "event_key_employ_record"//雇佣记录

        const val EVENT_KEY_EMPLOY_RECORD_SIGN_UP = "event_key_employ_record_sign_up"//雇佣记录 - 已报名
        const val TAG_GET_EMPLOYEE_LIST_SUCCESS = "tag_get_employee_list_success"//获取报名列表成功
        const val TAG_GET_EMPLOYEE_LIST_ERROR = "tag_get_employee_list_error"//获取报名列表失败
        const val TAG_ACCEPT_OR_REFUSED_RESUME_SUCCESS = "tag_accept_resume_success"//录取成功

        const val EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT = "event_key_employ_record_employment"//雇佣记录 - 已录用
        const val TAG_GET_EMPLOYEE_ACCEPT_LIST_SUCCESS = "tag_get_employee_accept_list_success"//获取录用列表成功
        const val TAG_GET_EMPLOYEE_ACCEPT_LIST_ERROR = "tag_get_employee_accept_list_error"//获取已录用列表数据失败

        const val EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY = "event_key_employ_record_report_duty"//雇佣记录 - 已到岗
        const val TAG_GET_REPORT_DUTY_LIST_SUCCESS = "tag_get_report_duty_list_success"//已到岗列表成功
        const val TAG_GET_REPORT_DUTY_LIST_ERROR = "tag_get_report_duty_list_error"//已到岗列表数据失败

        const val EVENT_KEY_EMPLOY_RECORD_WAIT_PAY = "event_key_employ_record_wait_pay" //雇佣记录 - 待结算
        const val TAG_GET_WAIT_PAY_LIST_SUCCESS = "tag_get_wait_pay_list_success"//待结算列表成功
        const val TAG_GET_WAIT_PAY_LIST_ERROR = "tag_get_wait_pay_list_error"//待结算列表数据失败
        const val TAG_WAIT_PAY_SETTLE_SUCCESS = "tag_wait_pay_settle_success"//结算完成


        const val EVENT_KEY_EMPLOY_RECORD_PAY = "event_key_employ_record_pay"//雇佣记录已结算
        const val TAG_GET_SETTLE_FINISH_LIST_SUCCESS = "tag_get_settle_finish_list_success"//获取雇佣记录已结算列表成功
        const val TAG_GET_SETTLE_FINISH_LIST_ERROR = "tag_get_settle_finish_list_error"//获取雇佣记录已结算列表成功

        //认证
        const val EVENT_KEY_COMPANY_ATTESTATION = "event_key_company_attestation" // 企业认证
        const val TAG_GET_COMPANY_ITEM_RESULT = "tag_get_company_item_result"
        const val TAG_GET_COMPANY_ATTESTATION_STATUS = "tag_get_company_attestation_status"
        const val EVENT_KEY_PEOPLE_ATTESTATION = "event_key_people_attestation" // 身份认证
        const val TAG_PEOPLE_ATTESTATION_SUBMIT_SUCCESS = "tag_people_attestation_submit_success" //上传成功

        const val EVENT_KEY_WAIT_PUBLISH_JOB = "event_key_wait_publish_job"//等待发布
        const val TAG_GET_WAIT_PUBLISH_JOB_LIST_SUCCESS = "tag_get_wait_publish_job_list_success"//
        const val TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR = "tag_get_wait_publish_job_list_error"//
        const val TAG_WAIT_PUBLISH_REFRESH_JOB_LIST = "tag_wait_publish_refresh_job_list"//刷新工作列表
        const val TAG_DELETE_WAIT_PUBLISH_JOB_SUCCESS = "tag_delete_wait_publish_job_success"//取消发布成功

        const val EVENT_KEY_WAIT_EXAMINE_JOB = "event_key_wait_examine_job"//等待审核
        const val TGA_GET_WAIT_EXAMINE_JOB_LIST_SUCCESS = "tga_get_wait_examine_job_list_success"
        const val TAG_GET_WAIT_EXAMINE_JOB_LIST_ERROR = "tag_get_wait_examine_job_list_error"
        const val TAG_WAIT_EXAMINE_REFRESH_JOB_LIST = "tag_wait_examine_refresh_job_list"//刷新工作列表
        const val TAG_DELETE_WAIT_EXAMINE_JOB_SUCCESS = "tag_delete_wait_examine_job_success"//取消订单成功

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

        const val EVENT_KEY_ORDER_DETAILS = "event_key_order_details"//订单详情
        const val TAG_GET_SIGN_UP_QR_CODE_SUCCESS = "tag_get_sign_up_qr_code_success" //获取签到二维码

        //雇员  EmployeeSignUp
        const val EVENT_KEY_EMPLOYEE_SIGN_UP = "event_key_employee_sign_up"//雇员-已报名
        const val TAG_GET_EMPLOYEE_SIGN_LIST_SUCCESS = "tag_get_employee_sign_list_success"//已报名列表
        const val TAG_GET_EMPLOYEE_SIGN_LIST_ERROR = "tag_get_employee_sign_list_error"//已报名列表失败

        const val EVENT_KEY_EMPLOYEE_EMPLOYMENT = "event_key_employee_employment"//雇员 - 已录用
        const val TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_SUCCESS = "tag_get_employee_employment_list_success"//已录用列表
        const val TAG_GET_EMPLOYEE_EMPLOYMENT_LIST_ERROR = "tag_get_employee_employment_list_error"//已录用列表失败
        const val TAG_ACCEPT_REFUSE_OFFER_SUCCESS = "tag_accept_refuse_offer_success" //接受拒绝请求成功

        const val EVENT_KEY_EMPLOYEE_REPORT_DUTY = "event_key_employee_report_duty"//雇员 - 已到岗
        const val TAG_GET_EMPLOYEE_REPORT_DUTY_LIST_SUCCESS = "tag_get_employee_report_duty_list_success"//已到岗列表
        const val TAG_GET_EMPLOYEE_REPORT_DUTY_LIST_ERROR = "tag_get_employee_report_duty_list_error"//已到岗列表失败

        const val EVENT_KEY_EMPLOYEE_PAY_FINISH = "event_key_employee_pay_finish"//雇员 - 已结算
        const val TAG_GET_EMPLOYEE_PAY_FINISH_LIST_SUCCESS = "tag_get_employee_pay_finish_list_success"//已结算列表
        const val TAG_GET_EMPLOYEE_PAY_FINISH_LIST_ERROR = "tag_get_employee_pay_finish_list_error"//已结算列表失败
        const val TAG_REFUSE_ACCEPT_SETTLE_FINISH = "tag_refuse_accept_settle_finish"//拒绝或接受结算成功


        const val EVENT_KEY_JOB_MANAGER = "event_key_job_manager" // 兼职管理界面

        const val EVENT_KEY_JOB_SEARCH = "event_key_job_search" // 工作搜索界面


        const val EVENT_KEY_SEARCH_JOB_LIST = "event_key_search_job_list" // 搜职位的Fragment
        const val TAG_GET_SEARCH_JOB_LIST_SUCCESS = "tag_get_search_job_list_success"//搜素职位成功
        const val TAG_GET_SEARCH_JOB_LIST_ERROR = "tag_get_search_job_list_error"//搜素职位失败


        const val EVENT_KEY_SEARCH_COMPANY_LIST = "event_key_search_company_list"//搜公司得Fragment
        const val TAG_GET_SEARCH_COMPANY_LIST_SUCCESS = "tag_get_search_company_list_success"//搜素职位成功
        const val TAG_GET_SEARCH_COMPANY_LIST_ERROR = "tag_get_search_company_list_error"//搜素职位失败

        const val EVENT_KEY_COMPANY_DETAILS = "event_key_company_details"//公司详情界面
        const val TAG_GET_COMPANY_JOB_LIST_SUCCESS = "tag_get_company_job_list_success"//获取公司发布的兼职列表


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

        const val EVENT_KEY_WORK = "event_key_work"//工作界面
        const val TAG_EMPLOYEE_ARRIVAL_SUCCESS = "tag_employee_arrival_success"//签到成功

        const val EVENT_JOB_DETAILS = "event_job_details" //工作详情
        const val TAG_GET_JOB_DETAILS_SUCCESS = "tag_get_job_details_success"//获取工作详情成功
        const val TAG_SIGN_UP_JOB_SUCCESS = "tag_sign_up_job_success"//报名成功
        const val TAG_SIGN_UP_RESUME_NOT_EXIST = "tag_sign_up_resume_not_exist"//简历不存在
        const val TAG_GET_EMPLOYER_DETAILS_SUCCESS = "tag_get_employer_details_success"//获取雇主的信息成功
        const val TAG_COLLECTION_STATUS_CHANGE = "tag_collection_status_change"//收藏状态改变

        const val EVENT_KEY_ALL_JOB_SCREEN = "event_key_all_job_screen"//全部兼职列表
        const val TAG_GET_JOB_TYPE_SUCCESS = "tag_get_job_type_success"//获取兼职类型
        const val TAG_GET_JOB_ITEM_LIST_SUCCESS = "tag_get_job_item_list_success" //获取兼职列表成功
        const val TAG_GET_JOB_ITEM_LIST_ERROR = "tag_get_job_item_list_error"//获取兼职列表失败

        const val EVENT_KEY_RESUME_DETAILS = "event_key_resume_details"//简历详情
        const val TAG_GET_RESUME_DETAILS_SUCCESS = "tag_get_resume_details_success"//获取用户简历成功

        const val EVENT_KEY_MY_COLLECTION_JOB ="event_key_my_collection_job"//我的收藏列表
        const val TAG_GET_MY_COLLECTION_LIST_SUCCESS = "tag_get_my_collection_list_success" //获取收藏列表成功
        const val TAG_GET_MY_COLLECTION_LIST_ERROR= "tag_get_my_collection_list_error"//获取收藏列表失败
        const val TAG_CANCEL_COLLECTION_FINISH = "tag_cancel_collection_finish"//取消收藏完成

        const val EVENT_KEY_ALIPAY_ATTESTATION = "event_key_alipay_attestation"//绑定支付宝

        const val EVENT_MY_MESSAGE = "event_my_message" // 我的消息
        const val TAG_GET_MESSAGE_LIST_SUCCESS = "tag_get_message_list_success" // 获取消息列表成功
        const val TAG_GET_MESSAGE_LIST_ERROR = "tag_get_message_list_error" // 获取消息列表失败

    }
}