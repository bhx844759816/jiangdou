package com.jxqm.jiangdou.ui.order.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import io.reactivex.functions.Consumer

/**
 * Created by Administrator on 2019/9/22.
 */
class OrderPaymentRepository : BaseEventRepository() {
    /**
     * 获取订单详情
     */
    fun getOrderDetails(jobId: String) {
        addDisposable(
            apiService.getOrderDetails(jobId)
                .action {
                    LogUtils.i("获取订单详情:$it")
                    sendData(Constants.EVENT_KEY_PAYMENT_ORDER, Constants.TAG_GET_ORDER_DETAILS_SUCCESS, it)
                }
        )


    }

    /**
     * 获取账户余额
     */
    fun getAccountBalance() {
        addDisposable(
            apiService.getAccountBalance()
                .compose(applySchedulers())
                .subscribe(
                    {
                        if (it.code == "0") {
                            sendData(
                                Constants.EVENT_KEY_PAYMENT_ORDER,
                                Constants.TAG_GET_USER_ACCOUNT_BALANCE_SUCCESS,
                                it.data
                            )
                        }
                    }, {

                    }
                )
        )
    }

    /**
     * 支付订单
     */
    fun payOrder(orderId:String,jobId: String) {
        addDisposable(
            apiService.payOrder(orderId,jobId)
                .action {
                    sendData(
                        Constants.EVENT_KEY_PAYMENT_ORDER,
                        Constants.TAG_PAY_ORDER_SUCCESS,
                        true
                    )
                }
        )
    }
}