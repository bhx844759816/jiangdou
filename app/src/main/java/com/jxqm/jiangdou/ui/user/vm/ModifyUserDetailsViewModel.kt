package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.ModifyUserDetailsRepository
import java.io.File

class ModifyUserDetailsViewModel : BaseViewModel<ModifyUserDetailsRepository>() {

    fun updateUserInfo(paramsMap: Map<String, String>, fileMaps: Map<String, File>) {
        mRepository.updateUserInfo(fileMaps,paramsMap)
    }
}