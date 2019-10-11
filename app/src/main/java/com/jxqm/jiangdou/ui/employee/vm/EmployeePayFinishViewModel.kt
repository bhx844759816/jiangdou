package com.jxqm.jiangdou.ui.employee.vm

import com.bhx.common.mvp.BaseView
import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employee.vm.repository.EmployeePayFinishRepository

/**
 * Created By bhx On 2019/9/24 0024 16:07
 */
class EmployeePayFinishViewModel : BaseViewModel<EmployeePayFinishRepository>() {
    fun getEmployeeSettleList() {
        mRepository.getEmployeeSettleList()
    }

    /**
     * 拒绝结算
     */
    fun refuseSettle(jobWorkId: String) {
        mRepository.refuseSettle(jobWorkId)
    }

    /**
     * 接受结算
     */
    fun acceptSettle(jobWorkId: String) {
        mRepository.acceptSettle(jobWorkId)
    }
}