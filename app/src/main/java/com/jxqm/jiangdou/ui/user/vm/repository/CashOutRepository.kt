package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import okhttp3.RequestBody

/**
 * 提现
 */
class CashOutRepository : BaseEventRepository() {
    /**
     * 发送验证码
     */
    fun senSmsCode() {
        addDisposable(
            apiService.sendSmsCodeCashOut().action {
                sendData(
                    Constants.EVENT_CASH_OUT,
                    Constants.TAG_CASH_OUT_SEND_SMS_CODE_SUCCESS,
                    true
                )
            }
        )
    }

    fun sendCashOutRequest(params: Map<String, String>) {
        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody =
                RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        addDisposable(apiService.cashOutMoney(requestBodyMaps).action {
            sendData(
                Constants.EVENT_CASH_OUT,
                Constants.TAG_CASH_OUT_SUCCESS,
                true
            )
        })
    }
}