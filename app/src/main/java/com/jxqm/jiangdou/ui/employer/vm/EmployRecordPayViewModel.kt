package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordPayRepository

/**
 * 雇佣记录 - 已结算
 * Created By bhx On 2019/9/24 0024 15:38
 */
class EmployRecordPayViewModel : BaseViewModel<EmployRecordPayRepository>() {
    private var pageNo = 1
    private var pageSize = 10

    fun getSettleListByStatus(jobId: String, isRefresh: Boolean, status: Int) {
        when (status) {
            0 -> { //出账
                mRepository.getSettleFinishList(jobId, pageNo, pageSize) { requestCallBack() }
            }
            1 -> {//待确认
                mRepository.getSettleWaitConfirmList(jobId, pageNo, pageSize) { requestCallBack() }
            }
            2 -> {//拒绝
                mRepository.getSettleRefuseList(jobId, pageNo, pageSize) { requestCallBack() }
            }
        }
    }

    /**
     * 请求的回调
     */
    private fun requestCallBack() {
        pageNo++
    }
}