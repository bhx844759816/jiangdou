package com.jxqm.jiangdou.ui.order.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

class PaymentRepository : BaseEventRepository() {

    fun getAlipayOrderInfo(amount: String) {
        addDisposable(apiService.getAlipayOrderInfo(amount)
            .action {
                sendData(Constants.EVENT_KEY_PAYMENT, Constants.TAG_GET_ALIPAY_ORDER_INFO, it)
            })
    }
}