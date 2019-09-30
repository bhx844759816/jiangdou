package com.jxqm.jiangdou.ui.employee.vm

import com.bhx.common.mvvm.BaseViewModel
import com.jxqm.jiangdou.ui.employee.vm.repository.EmployeeEmploymentRepository

/**
 * Created By bhx On 2019/9/24 0024 16:09
 */
class EmployeeEmploymentViewModel : BaseViewModel<EmployeeEmploymentRepository>() {

    fun getEmployeeOfferList() {
        mRepository.getEmployeeOfferList()
    }
}