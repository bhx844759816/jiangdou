package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordWaitPayRepository

/**
 * 雇佣记录 - 待结算
 * Created By bhx On 2019/9/24 0024 15:48
 */
class EmployRecordWaitPayViewModel : BaseViewModel<EmployRecordWaitPayRepository>() {
    private var pageNo = 1
    private var pageSize = Constants.PAGE_SIZE
    fun getWaitPayList(jobId: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getWaitPayList(jobId, pageNo, pageSize) {
            pageNo++
        }
    }

    fun singleSettleWork(jobId: String, amount: String) {
        mRepository.singleSettleWork(jobId, amount)
    }

    fun mergeSettleWork(ids: List<Long>) {
        mRepository.mergeSettleWork(ids)
    }
}