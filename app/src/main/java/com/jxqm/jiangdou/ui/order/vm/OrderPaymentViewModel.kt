package com.jxqm.jiangdou.ui.order.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.order.vm.repository.OrderPaymentRepository

/**
 * 订单支付
 * Created by Administrator on 2019/9/22.
 */
class OrderPaymentViewModel : BaseViewModel<OrderPaymentRepository>() {
    /**
     * 获取订单详情
     */
    fun getOrderDetails(jobId: String) {
        mRepository.getOrderDetails(jobId)
    }

    /**
     * 获取账户余额
     */
    fun getAccountBalance() {
        mRepository.getAccountBalance()
    }

    /**
     * 支付订单
     */
    fun payOrder(orderId:String,jobId: String) {
        mRepository.payOrder(orderId,jobId)
    }
}