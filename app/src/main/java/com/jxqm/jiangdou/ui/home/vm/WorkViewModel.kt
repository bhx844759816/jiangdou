package com.jxqm.jiangdou.ui.home.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.home.vm.repository.WorkRepository

/**
 * Created by Administrator on 2019/8/20.
 */
class WorkViewModel : BaseViewModel<WorkRepository>() {
    /**
     * 签到
     */
    fun employeeArrival(id: String) {
        mRepository.employeeArrival(id)
    }

    fun getAttestationStatus(){
        mRepository.getAttestationStatus()
    }
}