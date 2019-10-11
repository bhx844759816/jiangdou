package com.jxqm.jiangdou.ui.order.vm.repository

import com.bhx.common.utils.LogUtils
import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action
import com.jxqm.jiangdou.http.applySchedulers
import com.jxqm.jiangdou.http.applySchedulersForLoadingDialog
import com.jxqm.jiangdou.ui.order.model.OrderDetailsModel
import io.reactivex.Observable
import io.reactivex.functions.Consumer

/**
 * Created by Administrator on 2019/9/22.
 */
class OrderPaymentRepository : BaseEventRepository() {

    fun getOrderDetail(jobId: String) {
        addDisposable(
            Observable.concat(apiService.getAccountBalance(), apiService.getOrderDetails(jobId))
                .compose(applySchedulersForLoadingDialog())
                .subscribe({
                    if (it.code == "0") {
                       if(it.data is String){
                           sendData(
                               Constants.EVENT_KEY_PAYMENT_ORDER,
                               Constants.TAG_GET_USER_ACCOUNT_BALANCE_SUCCESS,
                               it.data
                           )
                       }
                       if(it.data is OrderDetailsModel){
                           sendData(Constants.EVENT_KEY_PAYMENT_ORDER, Constants.TAG_GET_ORDER_DETAILS_SUCCESS, it.data)
                       }
                    }
                }, {

                })
        )

    }

    /**
     * 支付订单
     */
    fun payOrder(jobId: String) {
        addDisposable(
            apiService.payOrder(jobId.toLong())
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