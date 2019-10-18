package com.jxqm.jiangdou.ui.user.vm.repository

import com.google.gson.Gson
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer
import okhttp3.RequestBody

class MyCollectionJobRepository : BaseEventRepository() {
    /**
     * 获取收藏列表
     */
    fun getCollectionList(pageNo: Int, pageSize: Int, callBack: () -> Unit) {
        addDisposable(
            apiService.getCollectionList(pageNo, pageSize).compose(applySchedulers())
                .subscribe({
                    if (it.code == "0") {
                        if (it.data.records.isNotEmpty()) {
                            callBack.invoke()
                        }
                        sendData(
                            Constants.EVENT_KEY_MY_COLLECTION_JOB,
                            Constants.TAG_GET_MY_COLLECTION_LIST_SUCCESS, it.data.records
                        )
                    } else {
                        sendData(
                            Constants.EVENT_KEY_MY_COLLECTION_JOB,
                            Constants.TAG_GET_MY_COLLECTION_LIST_ERROR, it.message
                        )
                    }
                }, {
                    sendData(
                        Constants.EVENT_KEY_MY_COLLECTION_JOB,
                        Constants.TAG_GET_MY_COLLECTION_LIST_ERROR, it.localizedMessage
                    )
                })
        )
    }

    fun cancelCollectionList(ids: List<Int>) {
        val params = mutableMapOf("jobIds" to ids)
        val jsonString = Gson().toJson(params)
        val body = RequestBody.create(
            okhttp3.MediaType.parse("application/json;charset=UTF-8"),
            jsonString
        )
        addDisposable(
            apiService.cancelCollectionJobMerge(body).action {
                sendData(
                    Constants.EVENT_KEY_MY_COLLECTION_JOB,
                    Constants.TAG_CANCEL_COLLECTION_FINISH, true
                )
            })
    }
}