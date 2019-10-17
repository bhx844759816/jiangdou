package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.UserWalletRepository

class UserWalletViewModel : BaseViewModel<UserWalletRepository>() {
    fun getAccount() {
        mRepository.getAccount()
    }
}