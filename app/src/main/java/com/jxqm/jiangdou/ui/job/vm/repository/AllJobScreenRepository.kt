package com.jxqm.jiangdou.ui.job.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * 全部兼职的
 * Created by Administrator on 2019/10/3.
 */
class AllJobScreenRepository : BaseEventRepository() {

    /**
     * 获取全部兼职类型
     */
    fun getAllJobType() {
        addDisposable(
            apiService.getHomeJobType().compose(applySchedulers())
                .subscribe {
                    if (it.code == "0") {
                        sendData(Constants.EVENT_KEY_ALL_JOB_SCREEN, Constants.TAG_GET_JOB_TYPE_SUCCESS, it.data)
                    }
                }
        )
    }

    /**
     * 获取简历
     */
    fun getAllJobList(paramsMap: Map<String, String>, callBack: () -> Unit) {
        LogUtils.i("paramsMap=$paramsMap")
        addDisposable(
            apiService.getAllSearchJobList(paramsMap)
                .compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {//获取全部工作成功
                        if (it.data.records.size == it.data.total) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_ALL_JOB_SCREEN,
                            Constants.TAG_GET_JOB_ITEM_LIST_SUCCESS,
                            it.data.records
                        )
                    } else {
                        sendData(Constants.EVENT_KEY_ALL_JOB_SCREEN, Constants.TAG_GET_JOB_ITEM_LIST_ERROR, it.message)
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_ALL_JOB_SCREEN,
                        Constants.TAG_GET_JOB_ITEM_LIST_ERROR,
                        it.localizedMessage
                    )
                })
        )
    }
}