package com.jxqm.jiangdou.ui.employer.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers

/**
 * 等待发布
 * Created by Administrator on 2019/9/22.
 */
class WaitPublishRepository : BaseEventRepository() {


    /**
     * 等待发布
     */
    fun getWaitPublishJob(pageNo: Int, pageSize: Int, successCallBack: () -> Unit) {
        addDisposable(
            apiService.getWaitPublishJob(pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.isNotEmpty()) {
                            successCallBack.invoke()
                        }
                        //获取数据成功
                        sendData(
                            Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                            Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_SUCCESS, it.data
                        )
                    } else {
                        //获取数据失败
                        sendData(
                            Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                            Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR, it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                        Constants.TAG_GET_WAIT_PUBLISH_JOB_LIST_ERROR, it.localizedMessage
                    )
                })
        )
    }

    /**
     * 删除发布的职位
     */
    fun deletePublishJob(jobId: String) {
        addDisposable(
            apiService.deleteJobById(jobId)
                .action {
                    sendData(
                        Constants.EVENT_KEY_WAIT_PUBLISH_JOB,
                        Constants.TAG_DELETE_WAIT_PUBLISH_JOB_SUCCESS, jobId
                    )
                }
        )
    }

}