package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * 交易明细
 */
class TradeDetailsListRepository : BaseEventRepository() {
    /**
     * 获取收益列表
     */
    fun getTradeDetailsList(year: String, month: String) {
        addDisposable(apiService.getTradeDetailsList(year, month).action {
            sendData(Constants.EVENT_TRADE_DETAILS_LIST, Constants.TAG_GET_TRADE_DETAILS_LIST_SUCCESS, it)
        })
    }
}