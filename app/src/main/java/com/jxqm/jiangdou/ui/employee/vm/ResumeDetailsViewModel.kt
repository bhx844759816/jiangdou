package com.jxqm.jiangdou.ui.employee.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employee.vm.repository.ResumeDetailsRepository

/**
 * 简历详情
 * Created by Administrator on 2019/10/9.
 */
class ResumeDetailsViewModel : BaseViewModel<ResumeDetailsRepository>() {
    fun getUserResume(userId: Long) {
        mRepository.getUserResume(userId)
    }
}