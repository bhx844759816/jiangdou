package com.jxqm.jiangdou.ui.job.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.job.vm.repository.JobDetailsRepository

/**
 * Created By bhx On 2019/9/27 0027 10:45
 */
class JobDetailsViewModel : BaseViewModel<JobDetailsRepository>() {

    fun signUpJob(jobId: String) {
        mRepository.signUpJob(jobId)
    }
}