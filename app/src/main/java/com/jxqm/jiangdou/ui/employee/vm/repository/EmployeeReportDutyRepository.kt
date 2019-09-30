package com.jxqm.jiangdou.ui.employee.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * Created By bhx On 2019/9/24 0024 16:06
 */
class EmployeeReportDutyRepository : BaseEventRepository() {

    /**
     * 获取已到岗的职位列表
     */
    fun getEmployeeArrivalList() {
        addDisposable(
            apiService.getEmployeeArrivalList()
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        sendData(
                            Constants.EVENT_KEY_EMPLOYEE_REPORT_DUTY,
                            Constants.TAG_GET_EMPLOYEE_REPORT_DUTY_LIST_SUCCESS,
                            it.data
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_EMPLOYEE_REPORT_DUTY,
                            Constants.TAG_GET_EMPLOYEE_REPORT_DUTY_LIST_ERROR,
                            it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOYEE_REPORT_DUTY,
                        Constants.TAG_GET_EMPLOYEE_REPORT_DUTY_LIST_ERROR,
                        it.localizedMessage
                    )
                })

        )
    }
}