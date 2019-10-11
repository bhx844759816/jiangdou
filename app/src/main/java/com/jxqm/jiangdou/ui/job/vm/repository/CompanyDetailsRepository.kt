package com.jxqm.jiangdou.ui.job.vm.repository

import com.bhx.common.mvvm.BaseRepository
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created by Administrator on 2019/8/18.
 */
class CompanyDetailsRepository : BaseEventRepository() {
    /**
     * 获取雇主发布的兼职列表
     */
    fun getCompanyJobList(employerId: String) {
        addDisposable(
            apiService.getEmployerJobList(employerId)
                .action {
                    sendData(Constants.EVENT_KEY_COMPANY_DETAILS, Constants.TAG_GET_COMPANY_JOB_LIST_SUCCESS, it)
                }
        )
    }

}