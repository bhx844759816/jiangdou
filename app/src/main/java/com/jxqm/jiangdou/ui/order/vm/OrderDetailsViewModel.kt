package com.jxqm.jiangdou.ui.order.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.order.vm.repository.OrderDetailsRepository

/**
 * Created By bhx On 2019/9/26 0026 14:30
 */

class OrderDetailsViewModel : BaseViewModel<OrderDetailsRepository>() {
    /**
     * 获取订单详情
     */
    fun getOrderDetails(jobId: String) {
        mRepository.getOrderDetails(jobId)
    }
}