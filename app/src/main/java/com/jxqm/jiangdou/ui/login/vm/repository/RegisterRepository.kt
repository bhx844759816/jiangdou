package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * Created By bhx On 2019/8/6 0006 17:39
 */
class RegisterRepository : BaseEventRepository() {

    fun sendSmsCode(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        addDisposable(apiService.sendSmsCode(body).action {
            sendData(Constants.EVENT_KEY_REGISTER, Constants.TAG_REGISTER_GET_CODE_SUCCESS, true)

        })
    }

    fun register(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        apiService.register(body)
            .action {
                sendData(Constants.EVENT_KEY_REGISTER, Constants.TAG_REGISTER_SUCCESS, true)
        }
    }
}