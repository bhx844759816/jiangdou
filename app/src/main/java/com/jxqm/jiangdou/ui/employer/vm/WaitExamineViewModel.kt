package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employer.vm.repository.WaitExamineRepository

/**
 * Created by Administrator on 2019/9/22.
 */
class WaitExamineViewModel : BaseViewModel<WaitExamineRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = 10
    fun getWaitExamineJob(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getWaitExamineList(pageNo, pageSize) {
            pageNo++
        }
    }

    fun deletePublishJob(jobId: String) {
        mRepository.deletePublishJob(jobId)
    }
}