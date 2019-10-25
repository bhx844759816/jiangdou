package com.jxqm.jiangdou.ui.login.vm.repository

import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * 手机密码登录
 */
class LoginPhonePsdRepository : BaseEventRepository() {
    fun doLogin(params: Map<String, String>) {
        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        addDisposable(
            apiService.getToken(requestBodyMaps)
                .flatMap {
                    MyApplication.instance().saveToken(it)
                    return@flatMap apiService.getUserInfo()
                }.action {
                    sendData(Constants.EVENT_KEY_LOGIN_PHONE_PSD, Constants.TAG_LOGIN_PHONE_PSD_LOGIN_SUCCESS, it)
                }
        )
    }
}