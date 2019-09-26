package com.jxqm.jiangdou.ui.order.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created By bhx On 2019/9/26 0026 14:36
 */
class OrderDetailsRepository:BaseEventRepository(){
    /**
     * 获取订单详情
     */
    fun getOrderDetails(jobId: String) {
        addDisposable(
            apiService.getOrderDetails(jobId)
                .action {
                    LogUtils.i("获取订单详情:$it")
                    sendData(Constants.EVENT_KEY_ORDER_DETAILS, Constants.TAG_GET_ORDER_DETAILS_SUCCESS, it)
                }
        )


    }
}