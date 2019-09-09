package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.RegisterRepository

/**
 * Created By bhx On 2019/8/6 0006 17:38
 */
class RegisterViewModel : BaseViewModel<RegisterRepository>() {

    /**
     * 发送验证码
     */
    fun sendSmsCode(phone: String, deviceId: String) {
        val params = mapOf("phone" to phone, "deviceId" to deviceId, "smsType" to "0")
        mRepository.sendSmsCode(params)
    }

    /**
     * 注册
     */
    fun register(phone: String, deviceId: String, password: String, smsCode: String) {
        val params = mapOf("deviceId" to deviceId, "password" to password, "phone" to phone, "smsCode" to smsCode)
        mRepository.register(params)
    }
}