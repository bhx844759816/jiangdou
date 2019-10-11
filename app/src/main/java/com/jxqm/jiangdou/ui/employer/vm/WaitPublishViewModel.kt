package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employer.vm.repository.WaitPublishRepository

/**
 * Created by Administrator on 2019/9/22.
 */
class WaitPublishViewModel : BaseViewModel<WaitPublishRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = 10
    fun getWaitPublishJob(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getWaitPublishJob(pageNo, pageSize) {
            pageNo++
        }
    }

    fun deletePublishJob(jobId: String) {
      mRepository.deletePublishJob(jobId)
    }
}