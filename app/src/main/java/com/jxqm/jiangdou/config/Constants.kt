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
        //
        const val EVENT_KEY_LOGIN = "event_key_login" //登录界面
        const val TAG_LOGIN_RESULT = "tag_login_result" //

        const val EVENT_KEY_PHONE_LOGIN = "event_key_phone_login" //手机号登录几面

        const val EVENT_KEY_VERIFY_CODE = "event_key_verify_code" //验证码界面
        const val TAG_AGREE_CONTINUE = "tag_agree_continue" //验证码界面

        const val EVENT_KEY_FORGET_PSD = "event_key_forget_psd" //忘记密码界面

        const val EVENT_KEY_REGISTER = "event_key_register" //注册界面

        const val EVENT_KEY_SELECT_JOB_TYPE = "event_key_select_job_type"//选择兼职类型

        const val EVENT_KEY_PEOPLE_ATTESTATION = "event_key_people_attestation"


    }
}