package com.jxqm.jiangdou.ui.employee.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.model.JobEmployeeBaseModel
import com.jxqm.jiangdou.model.JobEmployeeTitleModel

/**
 * Created By bhx On 2019/9/24 0024 16:07
 */
class EmployeePayFinishRepository : BaseEventRepository() {


    /**
     * 获取雇员 - 已结算
     */
    fun getEmployeeSettleList() {
        addDisposable(
            apiService.getEmployeeSettleList()
                .compose(applySchedulers())
                .subscribe(
                    {
                        if (it.code == "0") {
                            sendData(
                                Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH,
                                Constants.TAG_GET_EMPLOYEE_PAY_FINISH_LIST_SUCCESS,
                                it.data
                            )
                        } else {
                            sendData(
                                Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH,
                                Constants.TAG_GET_EMPLOYEE_PAY_FINISH_LIST_ERROR,
                                it.message
                            )
                        }
                    }, {
                        sendData(
                            Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH,
                            Constants.TAG_GET_EMPLOYEE_PAY_FINISH_LIST_ERROR,
                            it.localizedMessage
                        )
                    })

        )

    }

    fun refuseSettle(jobWorkId: String) {
        addDisposable(
            apiService.refuseSettle(jobWorkId.toLong()).action {
                sendData(
                    Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH,
                    Constants.TAG_REFUSE_ACCEPT_SETTLE_FINISH,
                    true
                )

            }
        )
    }

    fun acceptSettle(jobWorkId: String) {
        addDisposable(
            apiService.acceptSettle(jobWorkId.toLong())
                .action {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_PAY_FINISH,
                        Constants.TAG_REFUSE_ACCEPT_SETTLE_FINISH,
                        true
                    )
                }
        )
    }

}