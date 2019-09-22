package com.jxqm.jiangdou.ui.employer.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers

/**
 * Created by Administrator on 2019/9/22.
 */
class WaitExamineRepository : BaseEventRepository() {

    /**
     * 获取等待审核列表
     */
    fun getWaitExamineList(pageNo: Int, pageSize: Int, successCallBack: () -> Unit) {
        addDisposable(
            apiService.getWaitExaminJob(pageNo, pageSize)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.isNotEmpty()) {
                            successCallBack.invoke()
                        }
                        //获取数据成功
                        sendData(
                            Constants.EVENT_KEY_WAIT_EXAMINE_JOB,
                            Constants.TGA_GET_WAIT_EXAMINE_JOB_LIST_SUCCESS, it.data
                        )
                    } else {
                        //获取数据失败
                        sendData(
                            Constants.EVENT_KEY_WAIT_EXAMINE_JOB,
                            Constants.TAG_GET_WAIT_EXAMINE_JOB_LIST_ERROR, it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_WAIT_EXAMINE_JOB,
                        Constants.TAG_GET_WAIT_EXAMINE_JOB_LIST_ERROR, it.localizedMessage
                    )
                })

        )
    }
}