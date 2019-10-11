package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employer.vm.repository.PublishingRepository

/**
 * Created by Administrator on 2019/9/22.
 */
class PublishingViewModel : BaseViewModel<PublishingRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = 10
    fun getPublishingJob(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getPublishingJob(pageNo, pageSize) {
            pageNo++
        }
    }
}