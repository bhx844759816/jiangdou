package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordEmploymentRepository

/**
 * 雇佣记录 - 已录用
 * Created By bhx On 2019/9/24 0024 15:30
 */
class EmployRecordEmploymentViewModel : BaseViewModel<EmployRecordEmploymentRepository>() {
    private var pageNo = 1
    private var pageSize = Constants.PAGE_SIZE
    /**
     *获取录用列表通过不同的状态
     * @param status 0已邀请 1已接受 2已拒绝 3未回复
     */
    fun getEmployListByStatus(jobId: String, isRefresh: Boolean, status: Int) {
        if (isRefresh) {
            pageNo = 1
        }
        when (status) {
            0 -> {//已邀请
                mRepository.getInviteEmployeeList(jobId, pageNo, pageSize) { requestCallBack() }
            }
            1 -> {//已接受
                mRepository.getAcceptEmployeeList(jobId, pageNo, pageSize) { requestCallBack() }
            }
            2 -> {//已拒绝
                mRepository.getRefuseEmployeeList(jobId, pageNo, pageSize) { requestCallBack() }
            }
            3 -> {//未回复
                mRepository.getNoReplyEmployeeList(jobId, pageNo, pageSize) { requestCallBack() }
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
     * 撤回录用
     */
    fun withdrawOffer(id:Long){
        mRepository.withDrawOffer(id)
    }

    /**
     * 重发录用
     */
    fun repeatOffer(id:Long){
        mRepository.repeatOffer(id)
    }
}