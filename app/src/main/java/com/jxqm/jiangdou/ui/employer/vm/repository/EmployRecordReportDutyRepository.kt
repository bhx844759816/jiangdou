package com.jxqm.jiangdou.ui.employer.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * 雇佣记录 - 已到岗
 * Created By bhx On 2019/9/24 0024 15:42
 */
class EmployRecordReportDutyRepository : BaseEventRepository() {
    /**
     * 获取到岗员工的简历列表
     */
    fun getReportDutyList(jobId: String, pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getReportDutyList(jobId.toLong(), pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.size >= it.data.pageSize) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY,
                            Constants.TAG_GET_REPORT_DUTY_LIST_SUCCESS, it.data.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY,
                            Constants.TAG_GET_REPORT_DUTY_LIST_ERROR, it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_EMPLOY_RECORD_REPORT_DUTY,
                        Constants.TAG_GET_REPORT_DUTY_LIST_ERROR, it.localizedMessage
                    )
                })
        )
    }
}