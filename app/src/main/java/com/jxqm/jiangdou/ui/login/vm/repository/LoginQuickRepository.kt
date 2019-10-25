package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * 快捷登录
 */
class LoginQuickRepository : BaseEventRepository() {

    fun sendSmsCode(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        addDisposable(apiService.sendSmsCode(body).action {
            LogUtils.i("获取验证码成功")
            sendData(Constants.EVENT_KEY_LOGIN_QUICK, Constants.TAG_LOGIN_QUICK_CODE_SUCCESS, true)
        })
    }
}