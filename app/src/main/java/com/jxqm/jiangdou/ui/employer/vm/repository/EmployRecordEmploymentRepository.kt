package com.jxqm.jiangdou.ui.employer.vm.repository

import com.bhx.common.utils.ToastUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.EmployeeResumeModel
import io.reactivex.functions.Consumer

/**
 * 雇佣记录 - 已录用
 * Created By bhx On 2019/9/24 0024 15:30
 */
class EmployRecordEmploymentRepository : BaseEventRepository() {

    fun getInviteEmployeeList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getInviteEmployeeList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.isNotEmpty()) {
                            callBack.invoke()
                        }
                        val list = it.data.records
                        sendSuccessResult(list)
                    } else {
                        sendErrorResult(it.message)
                    }
                }, {
                    sendErrorResult(it.localizedMessage)
                })
        )
    }

    /**
     * 获取雇佣记录 - 已接受
     */
    fun getAcceptEmployeeList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getAcceptEmployeeList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.size == it.data.total) {
                            callBack.invoke()
                        }
                        val list = it.data.records
                        sendSuccessResult(list)
                    } else {
                        sendErrorResult(it.message)
                    }
                }, {
                    sendErrorResult(it.localizedMessage)
                })
        )
    }

    /**
     * 获取雇佣记录 - 已拒绝
     */
    fun getRefuseEmployeeList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getRefuseEmployeeList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.size == it.data.total) {
                            callBack.invoke()
                        }
                        sendSuccessResult(it.data.records)
                    } else {
                        sendErrorResult(it.message)
                    }
                }, {
                    sendErrorResult(it.localizedMessage)
                })
        )
    }

    /**
     *  获取雇佣记录 - 未回复
     */
    fun getNoReplyEmployeeList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getNoReplyEmployeeList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.size == it.data.total) {
                            callBack.invoke()
                        }
                        sendSuccessResult(it.data.records)
                    } else {
                        sendErrorResult(it.message)
                    }
                }, {
                    sendErrorResult(it.localizedMessage)
                })
        )
    }

    /**
     * 发送获取成功的结果
     */
    private fun sendSuccessResult(list: List<EmployeeResumeModel>) {
        sendData(
            Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT,
            Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_SUCCESS, list
        )
    }

    private fun sendErrorResult(error: String) {
        sendData(
            Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT,
            Constants.TAG_GET_EMPLOYEE_ACCEPT_LIST_ERROR, error
        )
    }

    /**
     * 撤回录用
     */
    fun withDrawOffer(id: Long) {
        addDisposable(
            apiService.withdrawOffer(id).action {
                sendData(
                    Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT,
                    Constants.TAG_EMPLOY_RECORD_REPEAT_WITHDRAW_FINISH, true
                )
            }
        )
    }

    fun repeatOffer(id: Long) {
        addDisposable(
            apiService.repeatOffer(id).action {
                sendData(
                    Constants.EVENT_KEY_EMPLOY_RECORD_EMPLOYMENT,
                    Constants.TAG_EMPLOY_RECORD_REPEAT_WITHDRAW_FINISH, true
                )
                ToastUtils.toastShort("重发成功")
            }
        )
    }

}