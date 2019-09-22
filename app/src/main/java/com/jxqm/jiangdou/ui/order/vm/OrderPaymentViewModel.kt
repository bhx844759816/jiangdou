package com.jxqm.jiangdou.ui.order.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.order.vm.repository.OrderPaymentRepository

/**
 * Created by Administrator on 2019/9/22.
 */
class OrderPaymentViewModel : BaseViewModel<OrderPaymentRepository>() {

    public fun getOrderDetails(jobId: String) {
        mRepository.getOrderDetails(jobId)
    }

    public fun getAccountBalance() {
        mRepository.getAccountBalance()
    }
}