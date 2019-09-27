package com.jxqm.jiangdou.ui.login.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.login.vm.repository.LoadingRepository

/**
 * Created By bhx On 2019/9/26 0026 09:49
 */

class LoadingViewModel : BaseViewModel<LoadingRepository>() {

    fun getUserInfo() {
        mRepository.getUserInfo()
    }
}