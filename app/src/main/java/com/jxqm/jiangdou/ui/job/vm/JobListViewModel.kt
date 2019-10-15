package com.jxqm.jiangdou.ui.job.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.job.vm.repository.JobListRepository

/**
 * Created by Administrator on 2019/8/17.
 */
class JobListViewModel : BaseViewModel<JobListRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = 10
    fun getSearchJobList(paramsMap: MutableMap<String, String>, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        paramsMap["pageNo"] = pageNo.toString()
        paramsMap["pageSize"] = pageSize.toString()
        mRepository.getSearchJobList(paramsMap) {
            pageNo++
        }
    }
}