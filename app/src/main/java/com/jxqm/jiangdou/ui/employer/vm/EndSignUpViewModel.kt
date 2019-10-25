package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.ui.employer.vm.repository.EndSignUpRepository

/**
 * Created by Administrator on 2019/9/22.
 */
class EndSignUpViewModel : BaseViewModel<EndSignUpRepository>(){

    private var pageNo: Int = 1
    private var pageSize = Constants.PAGE_SIZE
    fun getEndSignUpPublishJob(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getEndSignUpPublishJob(pageNo, pageSize) {
            pageNo++
        }
    }

    fun deletePublishJob(jobId: String) {
        mRepository.deletePublishJob(jobId)
    }
}