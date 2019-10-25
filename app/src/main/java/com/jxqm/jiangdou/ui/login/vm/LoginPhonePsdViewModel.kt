package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.LoginPhonePsdRepository

/**
 * 手机密码登录
 */
class LoginPhonePsdViewModel : BaseViewModel<LoginPhonePsdRepository>() {
    fun doLogin(phone: String, passWord: String, deviceId: String) {
        val params = mapOf(
            "username" to phone, "password" to passWord,
            "grant_type" to "password", "client_id" to "jxdou_web",
            "client_secret" to "123456", "auth_type" to "password",
            "device_id" to deviceId
        )
        mRepository.doLogin(params)
    }
}