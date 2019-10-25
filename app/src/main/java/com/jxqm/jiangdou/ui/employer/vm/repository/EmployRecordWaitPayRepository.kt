package com.jxqm.jiangdou.ui.employer.vm.repository

import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import okhttp3.RequestBody

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
                        if (it.data.records.isNotEmpty()) {
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

    /**
     * 单独结算
     */
    fun singleSettleWork(jobId: String, amount: String) {
        addDisposable(apiService.singleSettleWork(jobId.toLong(), amount.toInt())
            .action {
                sendData(
                    Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY,
                    Constants.TAG_WAIT_PAY_SETTLE_SUCCESS, true
                )
            })
    }

    /**
     * 合并结算
     */
    fun mergeSettleWork(ids: List<Long>) {
        val params = mutableMapOf("ids" to ids)
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
            jsonString
        )
        addDisposable(apiService.mergeSettleWork(body).action {
            sendData(
                Constants.EVENT_KEY_EMPLOY_RECORD_WAIT_PAY,
                Constants.TAG_WAIT_PAY_SETTLE_SUCCESS, true
            )
        })
    }
}