package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.CashOutRepository

class CashOutViewModel : BaseViewModel<CashOutRepository>() {

    fun senSmsCode() {
        mRepository.senSmsCode()
    }

    fun sendCashOutRequest(params: Map<String, String>) {
        mRepository.sendCashOutRequest(params)
    }
}