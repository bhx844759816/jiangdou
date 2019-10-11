package com.jxqm.jiangdou.ui.employee.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * 简历详情
 * Created by Administrator on 2019/10/9.
 */
class ResumeDetailsRepository : BaseEventRepository() {

    fun getUserResume(userId: Long) {
        addDisposable(
            apiService.getUserResume(userId)
                .action {
                    sendData(
                        Constants.EVENT_KEY_RESUME_DETAILS,
                        Constants.TAG_GET_RESUME_DETAILS_SUCCESS,
                        it
                    )
                }
        )
    }
}