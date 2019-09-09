package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.PhoneLoginRepository

/**
 * Created By bhx On 2019/8/6 0006 11:31
 */
class PhoneLoginViewModel : BaseViewModel<PhoneLoginRepository>() {
    /**
     * 登录
     */
    public fun doLogin(phone: String, passWord: String, deviceId: String) {
        val params = mapOf(
            "username" to phone, "password" to passWord,
            "grant_type" to "password", "client_id" to "jxdou_web",
            "client_secret" to "123456", "auth_type" to "password",
            "device_id" to deviceId
        )
        mRepository.doLogin(params)
    }
}