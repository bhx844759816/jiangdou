package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.TradeDetailsListRepository

/**
 * 交易明细
 */
class TradeDetailsListViewModel : BaseViewModel<TradeDetailsListRepository>() {
    /**
     * 获取交易明细列表
     */
    fun getTradeDetailsList(year: String, month: String) {
        mRepository.getTradeDetailsList(year, month)
    }
}