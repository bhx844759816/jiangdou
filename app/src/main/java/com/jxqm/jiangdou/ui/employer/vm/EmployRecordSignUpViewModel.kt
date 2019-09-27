package com.jxqm.jiangdou.ui.employer.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employer.vm.repository.EmployRecordSignUpRepository

/**
 * 雇佣记录 - 已报名
 * Created By bhx On 2019/9/24 0024 15:08
 */
class EmployRecordSignUpViewModel : BaseViewModel<EmployRecordSignUpRepository>() {
    private var pageNo = 1
    private var pageSize = 20
    fun getSignUpEmployee(jobId: String, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getSignUpEmployee(jobId, pageNo, pageSize) {
            pageNo++
        }
    }

    /**
     * 录用 - 单个录取
     */
    fun acceptResume(id: String) {
        mRepository.acceptResume(id)
    }
}