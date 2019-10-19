package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * 忘记密码
 * Created By bhx On 2019/8/6 0006 16:47
 */
class ForgetPsdRepository : BaseEventRepository() {
    /**
     * 发送手机验证码
     */
    fun sendSmsCode(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
            jsonString
        )
        addDisposable(apiService.sendSmsCode(body).action {
            sendData(
                Constants.EVENT_KEY_FORGET_PSD,
                Constants.TAG_FORGET_PSD_GET_SMS_CODE_SUCCESS,
                true
            )

        })
    }

    fun modifyPsd(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
            jsonString
        )
        addDisposable(apiService.modifyPsd(body).action {
            sendData(
                Constants.EVENT_KEY_FORGET_PSD,
                Constants.TAG_FORGET_PSD_MODIFY_PSD_SUCCESS,
                true
            )

        })

    }
}