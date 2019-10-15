package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * 我的消息
 */
class MyMessageRepository : BaseEventRepository() {

    fun getMessageList(pageNo: Int, pageSize: Int, successCallBack: () -> Unit) {
        addDisposable(apiService.getMessageList(
            pageNo,
            pageSize
        ).compose(applySchedulers())
            .subscribe(
            {
                if (it.code == "0") {
                    if (it.data.records.isNotEmpty()) {
                        successCallBack.invoke()
                    }
                    //获取数据成功
                    sendData(
                        Constants.EVENT_MY_MESSAGE,
                        Constants.TAG_GET_MESSAGE_LIST_SUCCESS, it.data.records
                    )
                } else {
                    //获取数据失败
                    sendData(
                        Constants.EVENT_MY_MESSAGE,
                        Constants.TAG_GET_MESSAGE_LIST_ERROR, it.message
                    )
                }
            }, {
                sendData(
                    Constants.EVENT_MY_MESSAGE,
                    Constants.TAG_GET_MESSAGE_LIST_ERROR, it.localizedMessage
                )
            })
        )
    }
}