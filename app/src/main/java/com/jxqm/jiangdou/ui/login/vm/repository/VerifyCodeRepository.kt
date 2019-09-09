package com.jxqm.jiangdou.ui.login.vm.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.bhx.common.event.LiveBus
import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.TokenModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Created By bhx On 2019/8/6 0006 12:54
 */

class VerifyCodeRepository : BaseEventRepository() {


    fun sendSmsCode(params: Map<String, String>) {
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), jsonString)
        addDisposable(apiService.sendSmsCode(body).action {
            sendData(Constants.EVENT_KEY_VERIFY_CODE, Constants.TAG_SEND_SMS_CODE_RESULT, true)
        })
    }

    fun getToken(params: Map<String, String>) {
        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        addDisposable(
            apiService.getToken(requestBodyMaps)
                .compose(applySchedulers())
                .doOnSubscribe {
                    if (!it.isDisposed) {
                        LiveBus.getDefault()
                            .postEvent(Constants.EVENT_KEY_LOADING_DIALOG, Constants.TAG_LOADING_DIALOG, true)
                    }
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    LiveBus.getDefault()
                        .postEvent(Constants.EVENT_KEY_LOADING_DIALOG, Constants.TAG_LOADING_DIALOG, false)
                }
                .subscribe({
                    sendData(Constants.EVENT_KEY_VERIFY_CODE, Constants.TAG_GET_TOKEN_RESULT, it)
                }, {
                    LogUtils.i("获取Token失败:${it.message}")
                    sendData(Constants.EVENT_KEY_VERIFY_CODE, Constants.TAG_GET_TOKEN_RESULT_ERROR, it)
                })
        )
    }
}