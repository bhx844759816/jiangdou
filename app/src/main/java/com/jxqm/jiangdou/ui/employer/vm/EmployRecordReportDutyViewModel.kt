package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordReportDutyRepository

/**
 * 雇佣记录 - 已到岗
 * Created By bhx On 2019/9/24 0024 15:42
 */
class EmployRecordReportDutyViewModel : BaseViewModel<EmployRecordReportDutyRepository>() {
    private var pageNo = 1
    private var pageSize = Constants.PAGE_SIZE

    fun getReportDutyList(jobId: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getReportDutyList(jobId, pageNo, pageSize) {
            pageNo++
        }
    }

}