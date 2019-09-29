package com.jxqm.jiangdou.ui.employer.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers

/**
 * 雇佣记录 - 待结算
 * Created By bhx On 2019/9/24 0024 15:45
 */
class EmployRecordWaitPayRepository : BaseEventRepository() {

    fun getWaitPayList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getWaitSettlementList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.size >= it.data.pageSize) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY,
                            Constants.TAG_GET_WAIT_PAY_LIST_SUCCESS, it.data.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY,
                            Constants.TAG_GET_WAIT_PAY_LIST_ERROR, it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY,
                        Constants.TAG_GET_WAIT_PAY_LIST_ERROR, it.localizedMessage
                    )
                })
        )
    }
}