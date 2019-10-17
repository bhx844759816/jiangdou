package com.jxqm.jiangdou.ui.user.vm.repository

import com.jxqm.jiangdou.config.Constants
import com.jxqm.jiangdou.http.BaseEventRepository
import com.jxqm.jiangdou.http.action

/**
 * 我的钱包的请求
 */
class UserWalletRepository : BaseEventRepository() {


    fun getAccount() {
        addDisposable(apiService.getAccount().action {
            sendData(Constants.EVENT_USER_WALLET, Constants.TAG_GET_USER_ACCOUNT, it)
        })
    }
}