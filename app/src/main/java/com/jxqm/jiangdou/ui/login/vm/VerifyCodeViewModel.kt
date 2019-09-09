package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.VerifyCodeRepository

/**
 * Created By bhx On 2019/8/6 0006 12:54
 */
class VerifyCodeViewModel : BaseViewModel<VerifyCodeRepository>() {

    /**
     * 调用登录接口
     */
    fun sendSmsCode(deviceId: String, phone: String) {
        val params = mapOf("phone" to phone, "deviceId" to deviceId, "smsType" to "1")
        mRepository.sendSmsCode(params)
    }

    fun getToken(deviceId: String, phone: String, code: String) {
        val params = mapOf(
            "username" to phone, "password" to code,
            "grant_type" to "password", "client_id" to "jxdou_web", "client_secret" to "123456", "auth_type" to "sms",
            "device_id" to deviceId
        )
        mRepository.getToken(params)
    }
}