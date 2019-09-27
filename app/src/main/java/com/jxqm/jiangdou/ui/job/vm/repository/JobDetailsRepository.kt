package com.jxqm.jiangdou.ui.job.vm.repository

import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created By bhx On 2019/9/27 0027 10:45
 */
class JobDetailsRepository : BaseEventRepository() {
    /**
     * 报名工作
     */
    fun signUpJob(jobId: String) {
        addDisposable(
            apiService.singUpJob(jobId.toLong()).action {

            }
        )
    }
}