package com.jxqm.jiangdou.ui.job.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.job.vm.repository.AllJobScreenRepository

/**
 * Created by Administrator on 2019/10/3.
 */
class AllJobScreenViewModel : BaseViewModel<AllJobScreenRepository>() {
    private var pageNo = 1
    private var pageSize = 10
    /**
     * 获取全部的工作类型
     */
    fun getAllJobType() {
        mRepository.getAllJobType()
    }

    fun getAllJobList(paramsMap: MutableMap<String, String>, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        paramsMap["pageNo"] = pageNo.toString()
        paramsMap["pageSize"] = pageSize.toString()
        mRepository.getAllJobList(paramsMap) {
            pageNo++
        }
    }
    fun refreshAllJobList(paramsMap: MutableMap<String, String>, isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        paramsMap["pageNo"] = pageNo.toString()
        paramsMap["pageSize"] = pageSize.toString()
        mRepository.refreshAllJobList(paramsMap) {
            pageNo++
        }
    }
}