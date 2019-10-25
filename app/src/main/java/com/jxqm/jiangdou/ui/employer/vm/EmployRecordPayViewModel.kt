package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordPayRepository

/**
 * 雇佣记录 - 已结算
 * Created By bhx On 2019/9/24 0024 15:38
 */
class EmployRecordPayViewModel : BaseViewModel<EmployRecordPayRepository>() {
    private var pageNo = 1
    private var pageSize = Constants.PAGE_SIZE

    fun getSettleListByStatus(jobId: String, isRefresh: Boolean, status: Int) {
        if(isRefresh){
            pageNo = 1
        }
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

    /**
     * 单节
     */
    fun singleSettleWork(jobId: String, amount: String) {
        mRepository.singleSettleWork(jobId, amount)
    }
}