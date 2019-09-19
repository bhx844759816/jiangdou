package com.jxqm.jiangdou.ui.publish.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * Created by Administrator on 2019/9/14.
 */
class JobPublishRepository : BaseEventRepository() {

    fun getAttestationDetails() {
        addDisposable(
            apiService.getAttestationStatus()
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_KEY_JOB_PUBLISH,
                            Constants.TAG_PUBLISH_JOB_ATTESTATION_DETAILS, it.data
                        )
                    }
                }, {
                    LogUtils.i("JobPublishRepository getAttestationDetails ${it.localizedMessage}")
                })
        )
    }
}