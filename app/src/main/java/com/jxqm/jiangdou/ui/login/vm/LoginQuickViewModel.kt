package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.LoginQuickRepository

class LoginQuickViewModel : BaseViewModel<LoginQuickRepository>() {

    /**
     * 调用登录接口
     */
    fun sendSmsCode(deviceId: String, phone: String) {
        val params = mapOf("phone" to phone, "deviceId" to deviceId, "smsType" to "1")
        mRepository.sendSmsCode(params)
    }
}