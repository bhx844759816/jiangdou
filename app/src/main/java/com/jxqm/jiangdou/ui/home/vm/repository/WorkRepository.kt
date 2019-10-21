package com.jxqm.jiangdou.ui.home.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.MyApplication
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created by Administrator on 2019/8/20.
 */
class WorkRepository : BaseEventRepository() {
    /**
     * 签到
     */
    fun employeeArrival(id: String) {
        addDisposable(apiService.employeeArrival(id.toLong())
            .action {
                ToastUtils.toastShort("签到成功")
            })
    }

    /**
     * 获取认证状态
     */
    fun getAttestationStatus() {
        addDisposable(apiService.getAttestationStatus().action {
            MyApplication.instance().attestationViewModel = it
            sendData(Constants.EVENT_KEY_WORK,Constants.TAG_GET_EMPLOYER_ATTESTATION_STATUS,true)
        })
    }
}