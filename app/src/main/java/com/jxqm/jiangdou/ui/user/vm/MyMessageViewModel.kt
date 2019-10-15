package com.jxqm.jiangdou.ui.user.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.user.vm.repository.MyMessageRepository

class MyMessageViewModel : BaseViewModel<MyMessageRepository>() {
    private var pageNo: Int = 1
    private var pageSize: Int = 10

    fun getMessageList(isRefresh: Boolean){
        if (isRefresh) {
            pageNo = 1
        }
        mRepository.getMessageList(pageNo, pageSize) {
            pageNo++
        }
    }
}