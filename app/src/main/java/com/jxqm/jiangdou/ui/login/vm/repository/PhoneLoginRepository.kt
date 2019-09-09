package com.jxqm.jiangdou.ui.login.vm.repository

import com.bhx.common.event.LiveBus
import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.LogUtils
import com.google.gson.Gson
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.RequestBody

/**
 * Created By bhx On 2019/8/6 0006 11:31
 */

class PhoneLoginRepository : BaseEventRepository() {

    public fun doLogin(params: Map<String, String>) {
        val requestBodyMaps = mutableMapOf<String, RequestBody>()
        params.forEach {
            val key = it.key
            val requestBody = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), it.value)
            requestBodyMaps[key] = requestBody
        }
        addDisposable(
            apiService.getToken(requestBodyMaps)
                .flatMap {
                    //存储TokenModel
                    MyApplication.instance().saveToken(it)
                    return@flatMap apiService.getUserInfo()
                }.action {
                    sendData(Constants.EVENT_KEY_VERIFY_CODE, Constants.TAG_GET_TOKEN_RESULT, it)
                }
        )
    }

}