package com.jxqm.jiangdou.ui.login.vm.repository

import android.annotation.SuppressLint
import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.Api
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.UserModel
import com.jxqm.jiangdou.ui.attestation.model.AttestationStatusModel
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * Created By bhx On 2019/9/26 0026 09:49
 */
class LoadingRepository : BaseEventRepository() {

    /**
     * 获取用户信息
     */
    @SuppressLint("CheckResult")
    fun getUserInfo() {
        //获取失败原因重新刷新Token
        addDisposable(
            Observable.merge(
                apiService.getUserInfo(),
                apiService.getAttestationStatus() //获取认证状态信息
            ).compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        when (it.data) {
                            is UserModel -> {
                                MyApplication.instance().userModel = it.data
                            }
                            is AttestationStatusModel -> {
                                MyApplication.instance().attestationViewModel = it.data
                            }
                        }
                    }
                }, {
                    sendData(Constants.EVENT_KET_LOADING, Constants.TAG_LOADING_FINISH, true)
                }, {
                    //请求完成
                    sendData(Constants.EVENT_KET_LOADING, Constants.TAG_LOADING_FINISH, true)
                })
        )
    }

}