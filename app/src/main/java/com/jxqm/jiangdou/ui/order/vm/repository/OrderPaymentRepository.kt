package com.jxqm.jiangdou.ui.order.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * Created by Administrator on 2019/9/22.
 */
class OrderPaymentRepository : BaseEventRepository() {
    /**
     * 获取订单详情
     */
    public fun getOrderDetails(jobId: String) {
        addDisposable(
            apiService.getOrderDetails(jobId)
                .action {
                    LogUtils.i("获取订单详情:$it")
                }
        )
    }

    public fun getAccountBalance() {
        addDisposable(
            apiService.getAccountBalance().action {
                LogUtils.i("获取账户余额$it")
            }
        )
    }
}