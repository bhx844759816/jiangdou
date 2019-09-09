package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * Created By bhx On 2019/8/6 0006 10:08
 */
class LoginRepository : BaseEventRepository() {

    fun sendSmsCode(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        apiService.sendSmsCode(body).action {
            LogUtils.i("获取验证码成功")
            sendData(Constants.EVENT_KEY_LOGIN, Constants.TAG_LOGIN_CODE_SUCCESS, true)
        }
    }
}