package com.jxqm.jiangdou.ui.job.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.job.vm.repository.JobDetailsRepository

/**
 * 工作详情的ViewModel
 * Created By bhx On 2019/9/27 0027 10:45
 */
class JobDetailsViewModel : BaseViewModel<JobDetailsRepository>() {

    fun getJobDetails(jobId: String) {
        mRepository.getJobDetails(jobId)
    }

    /**
     * 报名成功
     */
    fun signUpJob(jobId: String) {
        mRepository.signUpJob(jobId)
    }

    /**
     * 删除职位
     */
    fun deletePublishJob(jobId: String) {
        mRepository.deletePublishJob(jobId)
    }
}