package com.jxqm.jiangdou.ui.job.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.http.applySchedulersForLoadingDialog
import com.jxqm.jiangdou.model.JobDetailsWrapModel

/**
 * 搜索兼职
 * Created by Administrator on 2019/8/17.
 */
class JobListRepository : BaseEventRepository() {
    /**
     * 搜素兼职工作
     */
    fun getSearchJobList(paramsMap: Map<String, String>, callBack: () -> Unit) {
        addDisposable(
            apiService.getAllSearchJobList(paramsMap)
                .compose(applySchedulersForLoadingDialog())
                .subscribe({
                    if (it.code == "0") {
                        val jobDetailsWrapModel = it.data
                        if (jobDetailsWrapModel.records.size < jobDetailsWrapModel.total) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_SEARCH_JOB_LIST,
                            Constants.TAG_GET_SEARCH_JOB_LIST_SUCCESS,
                            jobDetailsWrapModel.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_SEARCH_JOB_LIST,
                            Constants.TAG_GET_SEARCH_JOB_LIST_ERROR,
                            it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_SEARCH_JOB_LIST,
                        Constants.TAG_GET_SEARCH_JOB_LIST_ERROR,
                        it.localizedMessage
                    )
                })
        )
    }
}