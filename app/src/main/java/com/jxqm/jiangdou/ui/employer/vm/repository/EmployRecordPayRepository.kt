package com.jxqm.jiangdou.ui.employer.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.HttpResult
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.EmployeeResumeModelWrap
import io.reactivex.functions.Consumer

/**
 * 雇佣记录 - 已结算
 * Created By bhx On 2019/9/24 0024 15:37
 */
class EmployRecordPayRepository : BaseEventRepository() {
    /**
     * 已出账列表
     */
    fun getSettleFinishList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getSettleFinishList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    sendSuccessResult(it,callBack)
                }, {
                 sendErrorResult(it.localizedMessage)
                })

        )

    }

    /**
     * 待确认
     */
    fun getSettleWaitConfirmList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getSettleWaitConfirmList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    sendSuccessResult(it,callBack)
                }, {
                    sendErrorResult(it.localizedMessage)
                })

        )
    }

    /**
     * 已拒绝
     */
    fun getSettleRefuseList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getSettleRefuseList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    sendSuccessResult(it,callBack)
                }, {
                    sendErrorResult(it.localizedMessage)
                })
        )
    }

    private fun sendSuccessResult(model: HttpResult<EmployeeResumeModelWrap>, callBack: () -> Unit) {
        if (model.code == "0") {
            if (model.data.records.size >= model.data.pageSize) {
                callBack.invoke()
            }
            sendData(
                Constants.EVENT_KEY_EMPLOY_RECORD_PAY,
                Constants.TAG_GET_SETTLE_FINISH_LIST_SUCCESS, model.data.records
            )
        } else {
            sendErrorResult(model.message)
        }
    }

    private fun sendErrorResult(message: String) {
        sendData(
            Constants.EVENT_KEY_EMPLOY_RECORD_PAY,
            Constants.TAG_GET_SETTLE_FINISH_LIST_ERROR, message
        )
    }

    /**
     * 单独结算
     */
    fun singleSettleWork(jobId: String, amount: String) {
        addDisposable(apiService.singleSettleWork(jobId.toLong(), amount.toInt())
            .action {
                sendData(
                    Constants.EVENT_KEY_EMPLOY_RECORD_PAY,
                    Constants.TAG_REPEAT_SETTLE_FINISH, true
                )
            })
    }
}